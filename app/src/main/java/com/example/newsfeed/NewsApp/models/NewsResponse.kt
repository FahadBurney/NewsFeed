package com.example.newsfeed.NewsApp.models

data class NewsResponse(
        val articles: MutableList<ArticlesItem>,
        val status: String,
        val totalResults: Int
)