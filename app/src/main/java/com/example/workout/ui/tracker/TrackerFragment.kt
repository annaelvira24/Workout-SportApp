package com.example.workout.ui.tracker

import android.Manifest
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.workout.AfterTrack
import com.example.workout.MainActivity
import com.example.workout.R
import com.example.workout.WorkoutApplication
import com.example.workout.database.History
import com.example.workout.other.Constants.ACTION_PAUSE_SERVICE
import com.example.workout.other.Constants.ACTION_START_OR_RESUME_SERVICE_CYCLING
import com.example.workout.other.Constants.ACTION_START_OR_RESUME_SERVICE_RUNNING
import com.example.workout.other.Constants.ACTION_STOP_SERVICE
import com.example.workout.other.Constants.POLYLINE_COLOR
import com.example.workout.other.Constants.POLYLINE_WIDTH
import com.example.workout.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.example.workout.other.TrackerUtility
import com.example.workout.services.Polyline
import com.example.workout.services.TrackerService
import com.example.workout.ui.history.HistoryViewModel
import com.example.workout.ui.history.HistoryViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import java.io.Serializable
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat


@AndroidEntryPoint
class TrackerFragment : Fragment(R.layout.fragment_tracker), EasyPermissions.PermissionCallbacks, SensorEventListener {

//    private val viewModel: TrackerModel by viewModels()
    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory((activity?.application as WorkoutApplication).historyDao)
    }


    private lateinit var cycling: ImageButton
    private lateinit var running: ImageButton
    private lateinit var cyclingText: TextView
    private lateinit var runningText: TextView
    private lateinit var start: Button
    private lateinit var stop: Button
    private lateinit var compass: ImageView
    private lateinit var timerView: TextView

    private lateinit var date : String
    private lateinit var timeStart : Timestamp

    private var exerciseID: Int? = null
    private var exerciseType = "Cycling"

    private var map: GoogleMap? = null

    private var sensor: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var magnetic: Sensor? = null

    private var stepsAmount = 0
    private var started = false

    private var isCycling = true
    private var isRunning = false
    private var arrG = FloatArray(3)
    private var arrGeo = FloatArray(3)
    private var azimuth = 0f
    private var currAzimuth = 0f

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()
    private var curTimeInMillis = 0L

    interface OnDataPass {
        fun onDataPass(exerciseID: Int)
    }

    lateinit var dataPasser: OnDataPass

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    private fun passData(exerciseID: Int) {
        dataPasser.onDataPass(exerciseID)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()

        exerciseID = (activity as MainActivity?)!!.getExercise()


        val format = SimpleDateFormat("yyyy-MM-dd")
        date = format.format(Date(System.currentTimeMillis()))

        timeStart = Timestamp(System.currentTimeMillis())

        cycling = requireView().findViewById(R.id.cyclingBtn)
        running = requireView().findViewById(R.id.runningBtn)
        cyclingText = requireView().findViewById(R.id.cyclingTxt)
        runningText = requireView().findViewById(R.id.runningTxt)
        start = requireView().findViewById(R.id.button)
        stop = requireView().findViewById(R.id.button2)
        timerView = requireView().findViewById(R.id.timerView)

        selectExercise()

        if (exerciseID == 0) {
            isCycling = true
            isRunning = false
            exerciseType = "Cycling"
        }
        else {
            isCycling = false
            isRunning = true
            exerciseType = "Walking"
        }

        if (isCycling) {
            ImageViewCompat.setImageTintList(
                    cycling,
                    ColorStateList.valueOf(Color.parseColor("#FF6200EE"))
            )
            cyclingText.setTextColor(Color.parseColor("#FF6200EE"))
        }
        else {
            ImageViewCompat.setImageTintList(
                    running,
                    ColorStateList.valueOf(Color.parseColor("#FF6200EE"))
            )
            runningText.setTextColor(Color.parseColor("#FF6200EE"))
        }

        start.setOnClickListener {
            Timber.d("Started!")
            started = true
            cycling.isEnabled = false
            running.isEnabled = false
            start.visibility = View.GONE

            val format = SimpleDateFormat("yyyy-MM-dd")
            date = format.format(Date(System.currentTimeMillis()))

            timeStart = Timestamp(System.currentTimeMillis())

            toggleRun()
        }

        stop.setOnClickListener {
            Timber.d("Stopped!")
            cycling.isEnabled = true
            running.isEnabled = true
            start.visibility = View.VISIBLE

            endRunAndSaveToDb()
        }

        compass = requireView().findViewById(R.id.compassImg)
        sensor = requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensor!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetic = sensor!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        subscribeToObservers()
    }

    private fun subscribeToObservers() {

        TrackerService.isTracking.observe(viewLifecycleOwner, {
            updateTracking(it)
        })

        TrackerService.pathPoints.observe(viewLifecycleOwner, {
            pathPoints = it
            addLatestPolyline()
//            moveCameraToUser()
        })

        TrackerService.timeRunInMillis.observe(viewLifecycleOwner, {
            curTimeInMillis = it
            val formattedTime = TrackerUtility.getFormattedStopWatchTime(curTimeInMillis, false)
            timerView.text = formattedTime
        })

        TrackerService.exerciseID.observe(viewLifecycleOwner, {
            updateExerciseID(it)
            Timber.d("Exercise ID: %s", it)
        })

        TrackerService.stepsAmount.observe(viewLifecycleOwner, {
            updateSteps(it)
        })

    }

    private fun selectExercise() {
        cycling.setOnClickListener {
            Timber.d("Cycling")
            exerciseType = "Cycling"
            ImageViewCompat.setImageTintList(
                    cycling,
                    ColorStateList.valueOf(Color.parseColor("#FF6200EE"))
            )
            ImageViewCompat.setImageTintList(
                    running,
                    ColorStateList.valueOf(Color.parseColor("#FF5A5A5A"))
            )
            cyclingText.setTextColor(Color.parseColor("#FF6200EE"))
            runningText.setTextColor(Color.parseColor("#FF5A5A5A"))
            isCycling = true
            isRunning = false
            start.isEnabled = true
            start.setBackgroundColor(Color.parseColor("#FF6200EE"))
            start.setTextColor(Color.parseColor("#FFFFFFFF"))
        }

        running.setOnClickListener {
            Timber.d("Running")
            exerciseType = "Walking"
            ImageViewCompat.setImageTintList(
                    running,
                    ColorStateList.valueOf(Color.parseColor("#FF6200EE"))
            )
            ImageViewCompat.setImageTintList(
                    cycling,
                    ColorStateList.valueOf(Color.parseColor("#FF5A5A5A"))
            )
            runningText.setTextColor(Color.parseColor("#FF6200EE"))
            cyclingText.setTextColor(Color.parseColor("#FF5A5A5A"))
            isRunning = true
            isCycling = false
            start.isEnabled = true
            start.setBackgroundColor(Color.parseColor("#FF6200EE"))
            start.setTextColor(Color.parseColor("#FFFFFFFF"))
        }
    }

    private fun toggleRun() {

        var e = if (isCycling) {
            0
        } else {
            1
        }

        passData(e)

        if(isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            if (isCycling) {
                Timber.d("Send cycling!")
                sendCommandToService(ACTION_START_OR_RESUME_SERVICE_CYCLING)
            }
            else {
                Timber.d("Send running!")
                sendCommandToService(ACTION_START_OR_RESUME_SERVICE_RUNNING)
            }
        }
    }

    private fun stopRun() {
        sendCommandToService(ACTION_STOP_SERVICE)
        timerView.text = "00:00:00"
    }

    private fun launchIntent(history: History){
        val detailIntent = Intent(activity, AfterTrack::class.java)
        detailIntent.putExtra("title", "History Logs");
        detailIntent.putExtra("history_obj", history as Serializable);

        activity?.startActivity(detailIntent);
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if(!isTracking) {
            start.visibility = View.VISIBLE
        } else {
            cycling.isEnabled = false
            running.isEnabled = false
            start.visibility = View.GONE
        }
    }

    private fun updateExerciseID(exerciseID: Int) {
        this.exerciseID = exerciseID
    }

    private fun updateSteps(stepsAmount: Int) {
        this.stepsAmount = stepsAmount
    }

    private fun requestPermissions() {
        if(TrackerUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permissions to use this app.",
                    REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permissions to use this app.",
                    REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun endRunAndSaveToDb() {
        var distanceInMeters = 0f
        for(polyline in pathPoints) {
            distanceInMeters += TrackerUtility.calculatePolylineLength(polyline).toInt()
        }
        val timeFinish = Timestamp(System.currentTimeMillis())
        var measure = 0f

        println(exerciseType)

        measure = if(exerciseType == "Cycling"){
            distanceInMeters
        } else{
            stepsAmount.toFloat()
        }

        var history = History(0, exerciseType, date, timeStart, timeFinish, measure)
//        val dateTimestamp = Calendar.getInstance().timeInMillis
//        var exercise: Exercise = if (exerciseID == 0) {
//            Exercise(exerciseID, dateTimestamp, distanceInMeters, curTimeInMillis, 0)
//        } else {
//            Exercise(exerciseID, dateTimestamp, distanceInMeters, curTimeInMillis, stepsAmount)
//        }
        viewModel.insert(history)
        println("Done saving history")
        stopRun()

        launchIntent(history)
    }

    private fun addAllPolylines() {
        for(polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun addLatestPolyline() {
        if(pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), TrackerService::class.java).also {
            it.action = action
            requireContext().startService(it)
            Timber.d("Sending command...")
        }

    override fun onResume() {
        super.onResume()

        sensor!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        sensor!!.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_GAME)

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
        sensor!!.unregisterListener(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor!!.type == Sensor.TYPE_ACCELEROMETER) {
                arrG[0] = 0.97f * arrG[0] + 0.03f * event.values[0]
                arrG[1] = 0.97f * arrG[1] + 0.03f * event.values[1]
                arrG[2] = 0.97f * arrG[2] + 0.03f * event.values[2]
            }
            if (event.sensor!!.type == Sensor.TYPE_MAGNETIC_FIELD) {
                arrGeo[0] = 0.97f * arrGeo[0] + 0.03f * event.values[0]
                arrGeo[1] = 0.97f * arrGeo[1] + 0.03f * event.values[1]
                arrGeo[2] = 0.97f * arrGeo[2] + 0.03f * event.values[2]
            }
        }

        var arrR = FloatArray(9)
        var arrT = FloatArray(9)
        var success = SensorManager.getRotationMatrix(arrR, arrT, arrG, arrGeo)

        if (success) {
            var arrO = FloatArray(3)
            SensorManager.getOrientation(arrR, arrO)
            azimuth = Math.toDegrees(arrO[0].toDouble()).toFloat()
            azimuth = (azimuth + 360) % 360

            var anim: Animation = RotateAnimation(
                    -currAzimuth,
                    -azimuth,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
            )
            currAzimuth = azimuth

            anim.duration = 500
            anim.repeatCount = 0
            anim.fillAfter = true

            compass.startAnimation(anim)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

}