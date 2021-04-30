package com.example.workout

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.View

import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import com.example.workout.database.Schedule
import com.example.workout.database.ScheduleDao
import com.example.workout.ui.history.ScheduleViewModelFactory
import com.example.workout.ui.history.SchedulerViewModel
import kotlinx.android.synthetic.main.fragment_tracker.*
import kotlinx.android.synthetic.main.history_logs2.view.*
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis
import java.sql.Date
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class SchedulerDetails: AppCompatActivity() {
    private val schedulerViewModel: SchedulerViewModel by viewModels {
        ScheduleViewModelFactory((application as WorkoutApplication).scheduleDao)
    }
    private val context: Context? = null

    lateinit var targetText: EditText
    lateinit var cautionText: TextView
    lateinit var startTimeSchedulePicker : TimePicker
    lateinit var finishTimeSchedulePicker : TimePicker
    lateinit var autoStarCheckBox : CheckBox
    lateinit var sun :Button
    lateinit var mon :Button
    lateinit var tue :Button
    lateinit var wed :Button
    lateinit var thu :Button
    lateinit var fri :Button
    lateinit var sat :Button
    lateinit var addButton : Button
    lateinit var updateButton : Button
    lateinit var deleteButton : Button
    lateinit var cyclingButton : ImageButton
    lateinit var runningButton : ImageButton
    private var repeatValue = 0
    lateinit var button_date: ImageButton
    lateinit var textview_date: TextView
    var cal = Calendar.getInstance()
    private var isCycling = true
    private var choosenDate = ""
    private var id = -1




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scheduler_details)


        cautionText = findViewById(R.id.cautionText)
        startTimeSchedulePicker =  findViewById(R.id.startTimeSchedule);
        startTimeSchedulePicker.setIs24HourView(true);
        finishTimeSchedulePicker =  findViewById(R.id.finishTimeSchedule);
        finishTimeSchedulePicker.setIs24HourView(true);
        autoStarCheckBox = findViewById(R.id.AutoStartcheckBox)
        addButton = findViewById(R.id.btnAdd)
        updateButton = findViewById(R.id.btnUpdate)
        deleteButton = findViewById(R.id.btnDelete)
        cyclingButton = findViewById(R.id.cyclingOption)
        runningButton = findViewById(R.id.runningOption)
        targetText = findViewById(R.id.target)
        sun = findViewById(R.id.sundayButton)
        mon = findViewById(R.id.mondayButton)
        tue = findViewById(R.id.tuesdayButton)
        wed = findViewById(R.id.wednesdayButton)
        thu = findViewById(R.id.thursdayButton)
        fri = findViewById(R.id.fridayButton)
        sat = findViewById(R.id.saturdayButton)
        textview_date = findViewById(R.id.viewDate)
        button_date = findViewById(R.id.buttonDate)

        textview_date!!.text = "____-__-__"


        val previousTintList = ColorStateList.valueOf(Color.parseColor("#FF5A5A5A"))
        val previousTextColor = mon.currentTextColor
        val checkedTintList = ColorStateList.valueOf(Color.parseColor("#FF03DAC5"))
        val checkedTextColor = Color.parseColor("#FF03DAC5")


        val title = this.intent.extras!!.getString("title")
        setTitle(title);

        if(intent.hasExtra("id")){
            addButton.setText("Cancel")
            id = intent.getIntExtra("id",-1)
            isCycling = intent.getStringExtra("exercise_type") == "Cycling"
            if(!isCycling){
                ImageViewCompat.setImageTintList(runningButton, checkedTintList)
                ImageViewCompat.setImageTintList(cyclingButton, previousTintList)
                targetText.setHint("Steps Target")
            }
            targetText.setText(intent.getFloatExtra("measure",0f).toString())
            autoStarCheckBox.isChecked =  intent.getBooleanExtra("autoTrack",false)
            startTimeSchedulePicker.hour = intent.getIntExtra("timeStartHour",0)
            startTimeSchedulePicker.minute = intent.getIntExtra("timeStartMinute",0)
            finishTimeSchedulePicker.hour = intent.getIntExtra("timeFinishHour",0)
            finishTimeSchedulePicker.minute = intent.getIntExtra("timeFinishMinute",0)
            if(intent.getStringExtra("date")!= "") {
                textview_date.setText(intent.getStringExtra("date"))
            }
            repeatValue = intent.getStringExtra("repeat").toInt()

            if(repeatValue>=1000000){
                sun.setTextColor(checkedTextColor)
            }
            if(repeatValue%1000000>=100000){
                mon.setTextColor(checkedTextColor)
            }
            if(repeatValue%100000>=10000){
                tue.setTextColor(checkedTextColor)
            }
            if(repeatValue%10000>=1000){
                wed.setTextColor(checkedTextColor)
            }
            if(repeatValue%1000>=100){
                thu.setTextColor(checkedTextColor)
            }
            if(repeatValue%100>=10){
                fri.setTextColor(checkedTextColor)
            }
            if(repeatValue%10>=1){
                sat.setTextColor(checkedTextColor)
            }
        }else{
            updateButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
            val params = addButton?.layoutParams as LinearLayout.LayoutParams
            params.width = LinearLayout.LayoutParams.MATCH_PARENT
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT
            addButton?.layoutParams = params
        }

        addButton.setOnClickListener {
            //To Do
            if(id != -1){
                finish()
            }else{
                try{
                    targetText.text.toString().toFloat()
                    val time = Time(currentTimeMillis())
                    val startTime = Time(startTimeSchedulePicker.hour, startTimeSchedulePicker.minute, 0)
                    val finishTime = Time(finishTimeSchedulePicker.hour, finishTimeSchedulePicker.minute, 0)
                    val exercise_type = if (isCycling) "Cycling" else "Walking"
                    //handle repeat date null
                    if(choosenDate == "" && repeatValue == 0){
                        if (time < startTime){
                            choosenDate = SimpleDateFormat("yyyy-MM-dd").format(Date(currentTimeMillis()))
                        }else{
                            choosenDate = SimpleDateFormat("yyyy-MM-dd").format(Date(currentTimeMillis()+24*3600*1000))
                        }
                    }
                    val schedule = Schedule(exercise_type, choosenDate, startTime, finishTime, repeatValue.toString().padStart(7, '0'), autoStarCheckBox.isChecked, targetText.text.toString().toFloat())
                    schedulerViewModel.insert(schedule)
                    finish()
                }catch (e : Exception){
                    cautionText.visibility = View.VISIBLE
                }
            }
        }

        updateButton.setOnClickListener {
            //To Do
            try {
                targetText.text.toString().toFloat()
                val time = Time(currentTimeMillis())
                val startTime = Time(startTimeSchedulePicker.hour, startTimeSchedulePicker.minute, 0)
                val finishTime = Time(finishTimeSchedulePicker.hour, finishTimeSchedulePicker.minute, 0)
                val exercise_type = if (isCycling) "Cycling" else "Walking"
                //handle repeat date null
                if(choosenDate == "" && repeatValue == 0){
                    if (time < startTime){
                        choosenDate = SimpleDateFormat("yyyy-MM-dd").format(Date(currentTimeMillis()))
                    }else{
                        choosenDate = SimpleDateFormat("yyyy-MM-dd").format(Date(currentTimeMillis()+24*3600*1000))
                    }
                }
                schedulerViewModel.update(id, exercise_type, choosenDate, startTime, finishTime, repeatValue.toString().padStart(7, '0'), autoStarCheckBox.isChecked, targetText.text.toString().toFloat())
                finish()
            }catch (e : Exception){
                cautionText.visibility = View.VISIBLE
            }

        }
        deleteButton.setOnClickListener {
            //To Do
            schedulerViewModel.delete(id)
            finish()
        }

        runningButton.setOnClickListener {
            ImageViewCompat.setImageTintList(runningButton, checkedTintList)
            ImageViewCompat.setImageTintList(cyclingButton, previousTintList)
            isCycling = false
            targetText.setHint("Steps Target")
        }
        cyclingButton.setOnClickListener {
            ImageViewCompat.setImageTintList(cyclingButton, checkedTintList)
            ImageViewCompat.setImageTintList(runningButton, previousTintList)
            isCycling = true
            targetText.setHint("Km Target")
        }

        sun.setOnClickListener {
            if(repeatValue<1000000){
                repeatValue += 1000000
//                sun.backgroundTintList = checkedTintList
                sun.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 1000000
//                sun.backgroundTintList = previousTintList
                sun.setTextColor(previousTextColor)
            }
        }

       mon.setOnClickListener {
            if(repeatValue%1000000 <100000){
                repeatValue += 100000
//                mon.backgroundTintList = checkedTintList
                mon.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 100000
//                mon.backgroundTintList = previousTintList
                mon.setTextColor(previousTextColor)
            }
        }
        tue.setOnClickListener {
            if(repeatValue%100000 <10000){
                repeatValue += 10000
//                tue.backgroundTintList = checkedTintList
                tue.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 10000
//                tue.backgroundTintList = previousTintList
                tue.setTextColor(previousTextColor)
            }
        }
        wed.setOnClickListener {
            if(repeatValue%10000 <1000){
                repeatValue += 1000
//                wed.backgroundTintList = checkedTintList
                wed.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 1000
//                wed.backgroundTintList = previousTintList
                wed.setTextColor(previousTextColor)
            }
        }
        thu.setOnClickListener {
            if(repeatValue%1000 <100){
                repeatValue += 100
//                thu.backgroundTintList = checkedTintList
                thu.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 100
//                thu.backgroundTintList = previousTintList
                thu.setTextColor(previousTextColor)
            }
        }
        fri.setOnClickListener {
            if(repeatValue%100 <10){
                repeatValue += 10
//                fri.backgroundTintList = checkedTintList
                fri.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 10
//                fri.backgroundTintList = previousTintList
                fri.setTextColor(previousTextColor)
            }
        }
        sat.setOnClickListener {
            if(repeatValue%10 <1){
                repeatValue += 1
//                sat.backgroundTintList = checkedTintList
                sat.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 1
//                sat.backgroundTintList = previousTintList
                sat.setTextColor(previousTextColor)
            }
        }

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button_date.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                 DatePickerDialog(this@SchedulerDetails,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).run {
                            also{datePicker.minDate = cal.timeInMillis}
                            show()
                        }

            }

        })
    }


    private fun updateDateInView() {
        val format = "yyyy-MM-dd"
        choosenDate = SimpleDateFormat(format, Locale.US).format(cal.getTime())
        textview_date.text = choosenDate
    }





}