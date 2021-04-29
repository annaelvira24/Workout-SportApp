package com.example.workout.History

import android.content.Context
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
import com.example.workout.ui.history.HistoryLogFragment2
import kotlinx.android.synthetic.main.history_item.view.*
import java.sql.Time


class HistoryListAdapter : ListAdapter<History, HistoryListAdapter.HistoryViewHolder>(
    HistoryComparator()
) {
    private val mContext: Context? = null
    private var date: String? = null

    fun setDate(date: String){
        this.date = date
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)


//        println(history.get(position).exercise_type)
//        println(history.get(position).timeStart)

        holder.view.setOnClickListener() {
            val appCompatActivity = it.context as AppCompatActivity

            val fragment: Fragment = HistoryLogFragment2(current)

//            supportFragmentManager!!.beginTransaction()
//                .add(R.id.fragmentContainer, HistoryLogFragment(date, dateUnformatted), "HistoryLogFragment").commit(
//            val fragmentManager: FragmentManager? = fragmentManager
            appCompatActivity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
//            val activity = holder.view.context
//            activity.getSupportFragmentbeginTransaction().replace(R.id.fragmentContainer, fragment).commit()
//            val detailIntent = Intent(holder.view.getContext(), NewsDetails::class.java)
//
//            detailIntent.putExtra("title", "Read Sport News");
//            detailIntent.putExtra("url", news.get(position).url.toString());

//            holder.view.getContext().startActivity(detailIntent);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var view: View = itemView
        private var history: History? = null

        fun bind(history: History) {
            this.history = history
            println(history.timeStart)
            view.history_type.text = (history.exercise_type)
            view.history_time.text = (Time(history.timeStart.time).toString() + " - " + Time(history.timeFinish.time).toString())
        }
    }

    class HistoryComparator : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.id == newItem.id
        }
    }

}