package com.example.newsfeed.NewsApp.api

import com.example.newsfeed.NewsApp.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    fun getBreakingNews(@Query(value = "country") country: String?, @Query(value = "category") category: String?,
                        @Query("apiKey") apiKey: String?): Call<NewsResponse?>?

    @GET("v2/everything")
    fun SearchNews(@Query("q") searchQuery: String?,
                   @Query("page") pageNumber: Int,
                   @Query("apiKey") apiKey: String?): Call<NewsResponse?>?
}