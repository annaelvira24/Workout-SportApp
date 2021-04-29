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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.History.HistoryListAdapter
import com.example.workout.R
import com.example.workout.WorkoutApplication
import com.example.workout.retrofitclient.*
import java.sql.Date

class HistoryLogFragment(var date : String, var dateUnformatted : String) : Fragment() {
//    private lateinit var logs : ArrayList<News>
    private lateinit var dateView: TextView
    private val newWordActivityRequestCode = 1
    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory((activity?.application as WorkoutApplication).historyDao)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.history_logs, container, false)
        val rvView: RecyclerView = root.findViewById(R.id.rvHistory)

        dateView = root.findViewById<TextView>(R.id.dateNow)
        dateView.text = date

        val adapter = HistoryListAdapter()
        adapter.setDate(date)
        rvView.adapter = adapter

        rvView.layoutManager = LinearLayoutManager(activity)


        println(dateUnformatted)
        historyViewModel.getHistoryByDate(dateUnformatted).observe(viewLifecycleOwner) { logs ->
            // Update the cached copy of the words in the adapter.
            logs.let { adapter.submitList(it) }

        }

        return root
    }
}