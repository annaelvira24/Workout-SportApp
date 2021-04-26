package com.example.workout.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.workout.R
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var calendarView: CalendarView
    private lateinit var dateView: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val current = LocalDateTime.now()
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        calendarView = root.findViewById<CalendarView>(R.id.calender)
        dateView = root.findViewById<TextView>(R.id.dateView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = dayOfMonth.toString() + "−" + (month + 1) + "−" + year
            dateView.text = date
        }

        dateView.text = formatter.format(current)

//        historyViewModel =
//                ViewModelProvider(this).get(HistoryViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_history, container, false)
//        val textView: TextView = root.findViewById(R.id.text_history)
//        historyViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}