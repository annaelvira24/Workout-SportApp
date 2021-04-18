package com.example.workout.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.workout.R

class SchedulerFragment : Fragment() {

    private lateinit var schedulerViewModel: SchedulerViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        schedulerViewModel =
                ViewModelProvider(this).get(SchedulerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_scheduler, container, false)
        val textView: TextView = root.findViewById(R.id.text_scheduler)
        schedulerViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}