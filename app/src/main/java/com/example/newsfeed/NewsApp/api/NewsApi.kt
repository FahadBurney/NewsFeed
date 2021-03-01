package com.example.newsfeed.NewsApp.api

import com.example.newsfeed.NewsApp.models.NewsResponse
import com.example.newsfeed.NewsApp.util.Constants.Companion.API_KEY
import com.example.newsfeed.NewsApp.util.Constants.Companion.API_KEY1
import com.example.newsfeed.NewsApp.util.Constants.Companion.API_KEY2
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
   suspend fun getCategoryNews(@Query(value = "country") country: String="in",
                               @Query(value = "category") category: String,
                               @Query("page")pageNumber: Int=1,
                        @Query("apiKey") apiKey: String= API_KEY2): Response<NewsResponse>

    @GET("v2/everything")
   suspend fun searchNews(@Query("q") searchQuery: String,
                   @Query("page") pageNumber: Int=1,
                   @Query("apiKey") apiKey: String= API_KEY2): Response<NewsResponse>
}