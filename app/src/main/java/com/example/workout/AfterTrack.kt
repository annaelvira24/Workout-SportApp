package com.example.workout

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.database.History
import com.example.workout.ui.history.EmptyTempFragment
import com.example.workout.ui.history.HistoryLogFragment
import com.example.workout.ui.history.HistoryLogFragment2


class AfterTrack : AppCompatActivity() {
    private val context: Context? = null
    private lateinit var history : History

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_details)

        val title = this.intent.extras!!.getString("title")
        history = this.intent.extras!!.get("history_obj") as History

        setTitle(title);

        val frameLayout1 = findViewById<FrameLayout>(R.id.fragmentContainer)
        var lParams: LinearLayout.LayoutParams? = frameLayout1.getLayoutParams() as LinearLayout.LayoutParams?
        lParams?.weight = 0f

        supportFragmentManager!!.beginTransaction()
                .add(R.id.fragmentContainer2, HistoryLogFragment2(history), "HistoryLogFragment").commit()
    }
}