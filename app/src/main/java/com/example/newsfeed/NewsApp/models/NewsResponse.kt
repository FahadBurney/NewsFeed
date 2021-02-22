package com.example.newsfeed.NewsApp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsResponse {
    @SerializedName("articles")
    @Expose
    val articles: List<ArticlesItem>? = null

    @SerializedName("totalResults")
    @Expose
    val totalResults = 0

    @SerializedName("status")
    @Expose
    val status: String? = null
}