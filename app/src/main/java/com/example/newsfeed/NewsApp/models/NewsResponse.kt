package com.example.newsfeed.NewsApp.models

data class NewsResponse(
        val articles: List<ArticlesItem>,
        val status: String,
        val totalResults: Int
)