package com.example.workout.ui.tracker

import android.content.Context.SENSOR_SERVICE
import android.content.res.ColorStateList
import android.graphics.Color
import android.hardware.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.example.workout.R

class TrackerFragment : Fragment(), SensorEventListener {

    private lateinit var cycling: ImageButton
    private lateinit var running: ImageButton
    private lateinit var start: ToggleButton
    private lateinit var compass: ImageView
    private var sensor: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var magnetic: Sensor? = null
    private var isCycling = false
    private var isRunning = false
    private var arrG = FloatArray(3)
    private var arrGeo = FloatArray(3)
    private var azimuth = 0f
    private var currAzimuth = 0f

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tracker, container, false)
        cycling = root.findViewById(R.id.cyclingBtn)
        running = root.findViewById(R.id.runningBtn)
        start = root.findViewById(R.id.startBtn)

        cycling.setOnClickListener {
            ImageViewCompat.setImageTintList(cycling, ColorStateList.valueOf(Color.parseColor("#FF6200EE")))
            ImageViewCompat.setImageTintList(running, ColorStateList.valueOf(Color.parseColor("#FF5A5A5A")))
            isCycling = true
            isRunning = false
            start.isEnabled = true
        }

        running.setOnClickListener {
            ImageViewCompat.setImageTintList(running, ColorStateList.valueOf(Color.parseColor("#FF6200EE")))
            ImageViewCompat.setImageTintList(cycling, ColorStateList.valueOf(Color.parseColor("#FF5A5A5A")))
            isRunning = true
            isCycling = false
            start.isEnabled = true
        }

        if (!isCycling && !isRunning) {
            start.isEnabled = false;
        }

        compass = root.findViewById(R.id.compassImg)
        sensor = requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensor!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetic = sensor!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)



        return root
    }

    override fun onResume() {
        super.onResume()
        sensor!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        sensor!!.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensor!!.unregisterListener(this)
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

            var anim: Animation = RotateAnimation(-currAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
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