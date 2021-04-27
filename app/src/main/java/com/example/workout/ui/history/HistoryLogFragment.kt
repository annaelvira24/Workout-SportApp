package com.example.workout.ui.history

import android.content.ContentValues
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.R
import com.example.workout.retrofitclient.*

class HistoryLogFragment(var date : String) : Fragment() {
//    private lateinit var logs : ArrayList<News>
    private lateinit var dateView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.history_logs, container, false)
        val rvView: RecyclerView = root.findViewById(R.id.rvHistory)

        dateView = root.findViewById<TextView>(R.id.dateNow)
        dateView.text = date

        val adapter = NewsAdapter()
        rvView.adapter = adapter

        rvView.layoutManager = LinearLayoutManager(activity)


        return root
    }
}