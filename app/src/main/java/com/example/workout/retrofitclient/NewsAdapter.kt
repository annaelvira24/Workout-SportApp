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
            val imageUrl = StringBuilder()
            imageUrl.append(news.urlToImage)
            Glide.with(view.context).load(imageUrl.toString()).into(view.news_image)
            view.news_title.text = (news.title)
            view.news_description.text = (news.description)
        }
    }

    fun setNews(news: ArrayList<News>) {
        this.news = news
        notifyDataSetChanged()
    }

    override fun getItemCount() = news.size
}