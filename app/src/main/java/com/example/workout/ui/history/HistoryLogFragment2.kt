package com.example.workout.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.History.HistoryListAdapter
import com.example.workout.Maps
import com.example.workout.R
import com.example.workout.WorkoutApplication
import com.example.workout.database.History
import java.sql.Time

class HistoryLogFragment2(var history: History ) : Fragment() {
    //    private lateinit var logs : ArrayList<News>
    private lateinit var dateView: TextView
    private lateinit var root: View
    private val newWordActivityRequestCode = 1
    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory((activity?.application as WorkoutApplication).historyDao)
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if(history.exercise_type == "Walking") {
            root = inflater.inflate(R.layout.history_logs2, container, false)

            dateView = root.findViewById<TextView>(R.id.dateNow2)
            root.findViewById<TextView>(R.id.type).text = "Exercise | " + history.exercise_type
            dateView.text = "Date | " + history.date

            root.findViewById<TextView>(R.id.time).text =
                "Time | " + (Time(history.timeStart.time).toString() + " - " + Time(history.timeFinish.time).toString())

            root.findViewById<TextView>(R.id.measure).text = history.measure.toInt().toString()
        }
        else{
            root = inflater.inflate(R.layout.history_logs3, container, false)

            dateView = root.findViewById<TextView>(R.id.dateNow2)
            root.findViewById<TextView>(R.id.type).text = "Exercise | " + history.exercise_type
            dateView.text = "Date | " + history.date

            root.findViewById<TextView>(R.id.time).text =
                    "Time | " + (Time(history.timeStart.time).toString() + " - " + Time(history.timeFinish.time).toString())

            root.findViewById<TextView>(R.id.measure).text = history.measure.toString()
            val button = root.findViewById<Button>(R.id.button_maps)
            button.setOnClickListener{
                val intent = Intent(activity, Maps::class.java)
                intent.putExtra("arrayList", history.track);
                startActivity(intent)
            }
        }


        return root
    }
}