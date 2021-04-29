package com.example.workout.ui.scheduler

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.workout.HistoryDetails
import com.example.workout.R
import com.example.workout.SchedulerDetails
import com.example.workout.ui.history.SchedulerViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SchedulerFragment : Fragment() {

    private lateinit var schedulerViewModel: SchedulerViewModel
    private lateinit var addButton: FloatingActionButton
    private  lateinit var schedulerListView : ListView




    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        schedulerViewModel =
                ViewModelProvider(this).get(SchedulerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_scheduler, container, false)
        schedulerListView = root.findViewById<ListView>(R.id.listSchedulerView)
        schedulerListView.setOnItemClickListener { _, _, i, _ ->
            val detailIntent = Intent(activity, SchedulerDetails::class.java)
            detailIntent.putExtra("title", "Edit Schedule")
            //To Do
//            detailIntent.putExtra("id", databaseid);
            activity!!.startActivity(detailIntent);

        }
        addButton = root.findViewById<FloatingActionButton>(R.id.addNewScheduleButton)
        addButton.setOnClickListener {
            val detailIntent = Intent(activity, SchedulerDetails::class.java)
            detailIntent.putExtra("title", "Add New Schedule");
            activity!!.startActivity(detailIntent);
        }
        return root
    }
}