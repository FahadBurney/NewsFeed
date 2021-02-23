package com.example.newsfeed.NewsApp.Repository

import com.example.newsfeed.NewsApp.Database.ArticlesDatabase
import com.example.newsfeed.NewsApp.api.RetrofitInstance

class NewsRepository(val database: ArticlesDatabase) {

    suspend fun getCategoryNews(countryCode:String,category:String,pageNumber:Int)=RetrofitInstance.api.getCategoryNews(countryCode,category)
}