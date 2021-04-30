package com.example.workout

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.ui.history.EmptyTempFragment
import com.example.workout.ui.history.HistoryLogFragment


class HistoryDetails : AppCompatActivity() {
    private val context: Context? = null
    private lateinit var date : String
    private lateinit var dateUnformatted : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_details)

        val title = this.intent.extras!!.getString("title")
        date = this.intent.extras!!.getString("date").toString()
        dateUnformatted = this.intent.extras!!.getString("dateUnformatted").toString()

        setTitle(title);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            val frameLayout1 = findViewById<FrameLayout>(R.id.fragmentContainer2)
            var lParams: LinearLayout.LayoutParams? = frameLayout1.getLayoutParams() as LinearLayout.LayoutParams?
            lParams?.weight = 0f
        }

        supportFragmentManager!!.beginTransaction()
                .add(R.id.fragmentContainer, HistoryLogFragment(date, dateUnformatted), "HistoryLogFragment").commit()

        supportFragmentManager!!.beginTransaction()
                .add(R.id.fragmentContainer2, EmptyTempFragment(), "HistoryLogFragment").commit()
    }
}