package com.example.newsfeed.NewsApp.UI

import android.os.Message
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.newsfeed.NewsApp.Repository.NewsRepository
import com.example.newsfeed.NewsApp.models.NewsResponse
import com.example.newsfeed.NewsApp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    val categoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var detailsNewspageNumber = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPageNumber = 1
    var message: String? = null

    fun getCategoryNews(countryCode: String, category: String) = viewModelScope.launch {
        categoryNews.postValue(Resource.Loading())
        val response = newsRepository.getCategoryNews(countryCode, category, detailsNewspageNumber)
        categoryNews.postValue(getBreakingNewsResponse(response))
    }

    fun getSearchNews(searchQuery:String) = viewModelScope.launch {
        //  val categoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery,searchNewsPageNumber)
        searchNews.postValue(getSearchNewsResponse(response))
    }

    private fun getBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun getSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun sendingCategory(msg: String) {
        message = msg
        message?.let { getCategoryNews("in", it) }
    }
}