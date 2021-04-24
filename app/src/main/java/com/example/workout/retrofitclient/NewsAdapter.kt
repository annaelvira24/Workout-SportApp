package com.example.workout.retrofitclient

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.telecom.Call.Details
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workout.NewsDetails
import com.example.workout.R
import kotlinx.android.synthetic.main.news_item.view.*


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val mContext: Context? = null
    private var news = emptyList<News>()
    private lateinit var webView: View
    private lateinit var webViewLayout: WebView


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news.get(position))
        holder.view.setOnClickListener() {
            val detailIntent = Intent(holder.view.getContext(), NewsDetails::class.java)

            detailIntent.putExtra("title", "Read Sport News");
            detailIntent.putExtra("url", news.get(position).url.toString());

            holder.view.getContext().startActivity(detailIntent);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        webView = LayoutInflater.from(parent.context).inflate(R.layout.news_webview, parent, false)
        return NewsViewHolder(view)
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var view: View = itemView
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