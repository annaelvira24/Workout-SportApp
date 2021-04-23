package com.example.workout.retrofitclient

import com.google.gson.annotations.SerializedName

data class News (
    @SerializedName("title") val title : String?,
    @SerializedName("description") val description: String?,
    @SerializedName("urlToImage") val urlToImage : String?,
    @SerializedName("url") val url : String?)