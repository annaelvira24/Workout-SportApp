package com.example.workout

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.ui.history.HistoryLogFragment
import android.app.DatePickerDialog
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SchedulerDetails : AppCompatActivity() {
    private val context: Context? = null
    lateinit var nameEditText: EditText
    lateinit var timeSchedulePicker : TimePicker
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
    private var repeatValue = 0
    lateinit var button_date: Button
    lateinit var textview_date: TextView
    var cal = Calendar.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scheduler_details)



        nameEditText = findViewById(R.id.name)
        timeSchedulePicker =  findViewById(R.id.timeSchedule);
        timeSchedulePicker.setIs24HourView(true);
        autoStarCheckBox = findViewById(R.id.AutoStartcheckBox)
        addButton = findViewById(R.id.btnAdd)
        updateButton = findViewById(R.id.btnUpdate)
        deleteButton = findViewById(R.id.btnDelete)
        sun = findViewById(R.id.sundayButton)
        mon = findViewById(R.id.mondayButton)
        tue = findViewById(R.id.tuesdayButton)
        wed = findViewById(R.id.wednesdayButton)
        thu = findViewById(R.id.thursdayButton)
        fri = findViewById(R.id.fridayButton)
        sat = findViewById(R.id.saturdayButton)
        textview_date = findViewById(R.id.viewDate)
        button_date = findViewById(R.id.buttonDate)

        textview_date!!.text = "--/--/----"


        val previousTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFFFF"))
        val previousTextColor = mon.currentTextColor
        val checkedTintList = ColorStateList.valueOf(Color.parseColor("#FF03DAC5"))
        val checkedTextColor = Color.WHITE


        val title = this.intent.extras!!.getString("title")
        setTitle(title);

        if(intent.hasExtra("id")){
            //ini buat yang udah ada. Klo Klik list viewnya
        }else{
            updateButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
        }

        addButton.setOnClickListener {
            //To Do
            finish()
        }
        updateButton.setOnClickListener {
            //To Do
            finish()
        }
        deleteButton.setOnClickListener {
            //To Do
            finish()
        }

        sun.setOnClickListener {
            if(repeatValue<1000000){
                repeatValue += 1000000
                sun.backgroundTintList = checkedTintList
                sun.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 1000000
                sun.backgroundTintList = previousTintList
                sun.setTextColor(previousTextColor)
            }
        }

       mon.setOnClickListener {
            if(repeatValue%1000000 <100000){
                repeatValue += 100000
                mon.backgroundTintList = checkedTintList
                mon.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 100000
                mon.backgroundTintList = previousTintList
                mon.setTextColor(previousTextColor)
            }
        }
        tue.setOnClickListener {
            if(repeatValue%100000 <10000){
                repeatValue += 10000
                tue.backgroundTintList = checkedTintList
                tue.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 10000
                tue.backgroundTintList = previousTintList
                tue.setTextColor(previousTextColor)
            }
        }
        wed.setOnClickListener {
            if(repeatValue%10000 <1000){
                repeatValue += 1000
                wed.backgroundTintList = checkedTintList
                wed.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 1000
                wed.backgroundTintList = previousTintList
                wed.setTextColor(previousTextColor)
            }
        }
        thu.setOnClickListener {
            if(repeatValue%1000 <100){
                repeatValue += 100
                thu.backgroundTintList = checkedTintList
                thu.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 100
                thu.backgroundTintList = previousTintList
                thu.setTextColor(previousTextColor)
            }
        }
        fri.setOnClickListener {
            if(repeatValue%100 <10){
                repeatValue += 10
                fri.backgroundTintList = checkedTintList
                fri.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 10
                fri.backgroundTintList = previousTintList
                fri.setTextColor(previousTextColor)
            }
        }
        sat.setOnClickListener {
            if(repeatValue%10 <1){
                repeatValue += 1
                sat.backgroundTintList = checkedTintList
                sat.setTextColor(checkedTextColor)
            }else{
                repeatValue -= 1
                sat.backgroundTintList = previousTintList
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
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date.text = sdf.format(cal.getTime())
    }





}