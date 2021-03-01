package com.example.newsfeed.NewsApp.Repository

import com.example.newsfeed.NewsApp.Database.ArticlesDatabase
import com.example.newsfeed.NewsApp.api.RetrofitInstance
import com.example.newsfeed.NewsApp.models.ArticlesItem

class NewsRepository(val database: ArticlesDatabase) {

    suspend fun getCategoryNews(countryCode: String, category: String, pageNumber: Int) =
            RetrofitInstance.api.getCategoryNews(countryCode, category, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
            RetrofitInstance.api.searchNews(searchQuery, pageNumber)

    suspend fun upsert(articlesItem: ArticlesItem) = database.getArticlesDao().upsert(articlesItem)

    fun getSavedArticles() = database.getArticlesDao().getAllArticles()

    suspend fun deleteArticles(articlesItem: ArticlesItem) = database.getArticlesDao().deleteArticles(articlesItem)
}