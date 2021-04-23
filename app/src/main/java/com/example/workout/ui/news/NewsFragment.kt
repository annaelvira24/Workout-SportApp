package com.example.workout.ui.news

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.R
import com.example.workout.retrofitclient.*
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {
    private lateinit var news : ArrayList<News>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_news, container, false)
        val rvView: RecyclerView = root.findViewById(R.id.rvNews)
        val adapter = NewsAdapter()
        rvView.adapter = adapter
        rvView.layoutManager = LinearLayoutManager(activity)

        val country = "id"
        val category = "sports"
        val apiKey = getString(R.string.api_key)

        val apiInterface : Api = RetrofitClient.getClient().create(Api::class.java)
        val call : Call<NewsList> = apiInterface.fetchNews(country, category, apiKey)
        call.enqueue(object : Callback<NewsList> {
            override fun onFailure(call: Call<NewsList>?, t: Throwable?) {
                Log.d("$TAG", "Failed fetch news")
            }
            override fun onResponse(call: Call<NewsList>?, response: Response<NewsList>?) {
                news = response!!.body()!!.articles
                adapter.setNews(news)
                Log.d("$TAG", news.toString())
            }

        })
        return root
    }
}