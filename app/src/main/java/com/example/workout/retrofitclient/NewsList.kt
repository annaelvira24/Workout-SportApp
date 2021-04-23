package com.example.workout.retrofitclient

data class NewsList (
    var status : String,
    var totalResult : Int,
    var articles : ArrayList<News>)