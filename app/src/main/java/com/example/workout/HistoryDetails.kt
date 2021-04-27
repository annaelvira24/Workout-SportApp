package com.example.workout

import android.content.Context
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.workout.ui.history.HistoryLogFragment

class HistoryDetails : AppCompatActivity() {
    private val context: Context? = null
    private lateinit var date : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_details)

        val title = this.intent.extras!!.getString("title")
        date = this.intent.extras!!.getString("date").toString()

        setTitle(title);

        supportFragmentManager!!.beginTransaction()
            .add(R.id.fragmentContainer, HistoryLogFragment(date), "HistoryLogFragment").commit()
    }
}