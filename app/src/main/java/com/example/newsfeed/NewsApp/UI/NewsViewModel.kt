package com.example.newsfeed.NewsApp.UI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.newsfeed.NewsApp.Repository.NewsRepository
import com.example.newsfeed.NewsApp.models.NewsResponse
import com.example.newsfeed.NewsApp.util.Resource
import com.example.newsfeed.NewsApp.util.Resource.*
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository): ViewModel() {

    val categoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val pageNumber = 1

   fun getCategoryNews(countryCode:String,category:String)=viewModelScope.launch {
       categoryNews.postValue(Loading())
       val response=newsRepository.getCategoryNews(countryCode,category,pageNumber)
       categoryNews.postValue(getBreakingNewsResponse(response))
   }

    private fun getBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let {  resultResponse->
                return Success(resultResponse)
            }
        }
        return Error(response.body())
    }
}