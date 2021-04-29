package com.example.workout.ui.scheduler

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.History.HistoryListAdapter
import com.example.workout.HistoryDetails
import com.example.workout.R
import com.example.workout.SchedulerDetails
import com.example.workout.WorkoutApplication
import com.example.workout.scheduler.SchedulerListAdapter
import com.example.workout.ui.history.HistoryViewModel
import com.example.workout.ui.history.HistoryViewModelFactory
import com.example.workout.ui.history.ScheduleViewModelFactory
import com.example.workout.ui.history.SchedulerViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SchedulerFragment : Fragment() {

    private lateinit var addButton: FloatingActionButton
    private  lateinit var schedulerListView : ListView

    private val schedulerViewModel: SchedulerViewModel by viewModels {
        ScheduleViewModelFactory((activity?.application as WorkoutApplication).scheduleDao)
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_scheduler, container, false)
        val rvView: RecyclerView = root.findViewById(R.id.rvSchedule)

        val adapter = SchedulerListAdapter()
        rvView.adapter = adapter

        rvView.layoutManager = LinearLayoutManager(activity)

        schedulerViewModel.getAllSchedule().observe(viewLifecycleOwner) { logs ->
            // Update the cached copy of the words in the adapter.
            logs.let { adapter.submitList(it) }

        }

//        val root = inflater.inflate(R.layout.fragment_scheduler, container, false)
//        schedulerListView = root.findViewById<ListView>(R.id.listSchedulerView)
//        schedulerListView.setOnItemClickListener { _, _, i, _ ->
//            val detailIntent = Intent(activity, SchedulerDetails::class.java)
//            detailIntent.putExtra("title", "Edit Schedule")
//            //To Do
////            detailIntent.putExtra("id", databaseid);
//            activity!!.startActivity(detailIntent);
//
//        }
        addButton = root.findViewById<FloatingActionButton>(R.id.addNewScheduleButton)
        addButton.setOnClickListener {
            val detailIntent = Intent(activity, SchedulerDetails::class.java)
            detailIntent.putExtra("title", "Add New Schedule");
            activity?.startActivity(detailIntent);
        }
        return root
    }
}