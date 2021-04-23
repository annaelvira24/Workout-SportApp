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

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var news : ArrayList<News>

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
//
//        rvMovies.layoutManager = GridLayoutManager(applicationContext, 2)
//
//        val apiKey = getString(R.string.api_key)
//        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
//        getLatestMovie(apiInterface, apiKey)
//        getPopularMovies(apiInterface, apiKey)
//
//        collapseImage.setOnClickListener {
//            Toast.makeText(applicationContext, "Poster Gede", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun getLatestMovie(apiInterface: ApiInterface, apiKey : String) : Movie? {
//        var movie : Movie? = null
//        val call : Call<Movie> = apiInterface.getMovieLatest(apiKey)
//        call.enqueue(object : Callback<Movie> {
//            override fun onFailure(call: Call<Movie>?, t: Throwable?) {
//                Log.d("$TAG", "Gagal Fetch Popular Movie")
//            }
//
//            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
//                if (response != null) {
//                    var originalTitle : String? = response.body()?.originalTitle
//                    var posterPath : String? = response.body()?.posterPath
//
//                    collapseToolbar.title = originalTitle
//                    if (posterPath == null) {
//                        collapseImage.setImageResource(R.drawable.icon_no_image)
//                    } else {
//                        val imageUrl = StringBuilder()
//                        imageUrl.append(getString(R.string.base_path_poster)).append(posterPath)
//                        Glide.with(applicationContext).load(imageUrl.toString()).into(collapseImage)
//                    }
//                }
//            }
//
//        })
//
//        return movie
//    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        newsViewModel =
//                ViewModelProvider(this).get(NewsViewModel::class.java)
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

//    fun getNews(apiInterface: Api, country : String, category: String, apiKey : String) {
//        val call : Call<NewsList> = apiInterface.fetchNews(country, category, apiKey)
//        Log.d("$TAG", "heiiii")
//        call.enqueue(object : Callback<NewsList> {
//            override fun onFailure(call: Call<NewsList>?, t: Throwable?) {
//                Log.d("$TAG", "Gagal Fetch Popular Movie")
//            }
//
//            override fun onResponse(call: Call<NewsList>?, response: Response<NewsList>?) {
//                news = response!!.body()!!.articles
//                rvNews.adapter = Adapter(news)
//                Log.d("$TAG", news.toString())
//            }
//
//        })
//    }
}