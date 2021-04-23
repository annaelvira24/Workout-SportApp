package com.example.workout.retrofitclient

import com.example.workout.retrofitclient.NewsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("top-headlines")    //End Url
    fun fetchNews(
            @Query("country") country: String,
            @Query("category") category: String,
            @Query("apiKey") apiKey: String) : Call<NewsList>
}

