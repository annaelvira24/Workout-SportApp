package com.example.workout.retrofitclient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workout.R
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var news = emptyList<News>()

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news.get(position))
        println(news.get(position).toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView
        private var news: News? = null

        fun bind(news: News) {
            this.news = news
            view.news_title.setText(news.title)
//            val imageUrl = StringBuilder()
        }
    }

    fun setNews(news: ArrayList<News>) {
        this.news = news
        println("please")
        notifyDataSetChanged()
    }

//    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val newsItemView: TextView = itemView.findViewById(R.id.news_title)
//
//        fun bind(text: String?) {
//            newsItemView.text = text
//            println(newsItemView.text)
//        }

//        companion object {
//            fun create(parent: ViewGroup): NewsViewHolder {
//                val view: View = LayoutInflater.from(parent.context)
//                        .inflate(R.layout.news_item, parent, false)
//                return NewsViewHolder(view)
//            }
//        }

//    }

    override fun getItemCount() = news.size
}

//    class NewsComparator : DiffUtil.ItemCallback<News>() {
//        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
//            return oldItem.title == newItem.title
//        }
//    }



    //
//    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
//        holder.bind(news.get(position))
//    }
//
//    override fun getItemCount() = news.size
//
//class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    private val wordItemView: TextView = itemView.findViewById(R.id.textView)
//
//    fun bind(text: String?) {
//        wordItemView.text = text
//    }
//
//    companion object {
//        fun create(parent: ViewGroup): WordViewHolder {
//            val view: View = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.recyclerview_item, parent, false)
//            return WordViewHolder(view)
//        }
//    }
//}


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
//        return NewsViewHolder(view)
//    }
//
//    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
//        private var view : View = itemView
//        private var news : News? = null
//
//        override fun onClick(p0: View?) {
//            Toast.makeText(view.context, "Item diklik", Toast.LENGTH_LONG).show()
//        }
//
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        fun bind(news: News) {
//            this.news = news
////            val imageUrl = StringBuilder()
////            imageUrl.append(view.context.getString(R.string.base_path_poster)).append(movie.posterPath)
//            view.news_title.setText(news.title)
//            view.news_author.setText(news.author)
//            view.news_description.setText(news.description)
//
//        }
//    }
//}

//class Adapter(private val context: Context, private val mNews: List<News>, private val mRowLayout: Int) : RecyclerView.Adapter<Adapter.NewsViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
//        return NewsViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
//        holder.positionNumber?.text = context.resources.getString(R.string.news_num, position + 1)
//        holder.title?.text = context.resources.getString(R.string.ques_title, mQuestions[position].title)
//        holder.link?.text = context.resources.getString(R.string.ques_link, mQuestions[position].link)
//
//        holder.containerView.setOnClickListener {
//            Toast.makeText(context, "Clicked on: " + holder.title.text, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return mNews.size
//    }
//
//    class NewsViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
//        val positionNumber = containerView.positionNumber;
//        val title = containerView.title;
//        val link = containerView.link;
//    }
//}