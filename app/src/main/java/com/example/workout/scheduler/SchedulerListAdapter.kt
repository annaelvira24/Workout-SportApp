package com.example.workout.scheduler

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.R
import com.example.workout.database.History
import com.example.workout.database.Schedule
import com.example.workout.ui.history.HistoryLogFragment2
import kotlinx.android.synthetic.main.history_item.view.*
import kotlinx.android.synthetic.main.schedule_item.view.*
import java.sql.Time

class SchedulerListAdapter(private val listener: (Schedule) -> Unit
)  : ListAdapter<Schedule, SchedulerListAdapter.ScheduleViewHolder>(
        ScheduleComparator()
) {
    private val mContext: Context? = null
    private var date: String? = null

    fun setDate(date: String){
        this.date = date
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            listener(current)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ScheduleViewHolder(view)
    }

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var view: View = itemView
        private var schedule: Schedule? = null

        fun bind(schedule: Schedule) {
            this.schedule = schedule
            view.schedule_type.text = (schedule.exercise_type)
            var i = 0;
            var count = 0;
            var repeat : String = ""
            for(i in 0..6){
                if(schedule.repeat?.get(i) == '1'){
                    count++
                    if(i == 0){
                        repeat += "Sun "
                    }
                    else if(i == 1){
                        repeat += "Mon "
                    }
                    else if(i == 2){
                        repeat += "Tue "
                    }
                    else if(i == 3){
                        repeat += "Wed "
                    }
                    else if(i == 4){
                        repeat += "Thu "
                    }
                    else if(i == 5){
                        repeat += "Fri "
                    }
                    else if(i == 6){
                        repeat += "Sat "
                    }
                }
            }
            if(count == 7){
                repeat = "Everyday"
            }
            else if(count == 0){
                repeat = schedule.date
            }
            view.schedule_date.text = (repeat)
            view.schedule_time.text = "Time: " + (Time(schedule.timeStart.time).toString() + " - " + Time(schedule.timeFinish.time).toString())
            if(schedule.autoTrack){
                view.auto_track.text = "Auto Track on"
            }
            else{
                view.auto_track.text = "Auto Track off"
            }
        }
    }

    class ScheduleComparator : DiffUtil.ItemCallback<Schedule>() {
        override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem.id == newItem.id
        }
    }

}