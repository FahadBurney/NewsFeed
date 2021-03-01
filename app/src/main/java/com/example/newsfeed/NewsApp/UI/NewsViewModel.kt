package com.example.newsfeed.NewsApp.UI

import android.os.Message
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.newsfeed.NewsApp.Repository.NewsRepository
import com.example.newsfeed.NewsApp.models.ArticlesItem
import com.example.newsfeed.NewsApp.models.NewsResponse
import com.example.newsfeed.NewsApp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    val categoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var categoryNewPageNumber = 1
    var categoryNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPageNumber = 1
    var message: String? = null
    var searchNewsResponse: NewsResponse? = null

    fun getCategoryNews(countryCode: String, category: String) = viewModelScope.launch {
        categoryNews.postValue(Resource.Loading())
        val response = newsRepository.getCategoryNews(countryCode, category, categoryNewPageNumber)
        categoryNews.postValue(getCategoryNewsResponse(response))
    }

    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
        //  val categoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPageNumber)
        searchNews.postValue(getSearchNewsResponse(response))
    }

    // database functionality

    fun savedArticle(articlesItem: ArticlesItem) = viewModelScope.launch {
        newsRepository.upsert(articlesItem)
    }

    fun getSavedNewsToDisplay() = newsRepository.getSavedArticles()

    fun deleteArticles(articlesItem: ArticlesItem) = viewModelScope.launch {
        newsRepository.deleteArticles(articlesItem)
    }


    private fun getCategoryNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse->
                categoryNewPageNumber++
                if(categoryNewsResponse==null)
                {
                    categoryNewsResponse=resultResponse
                }
                else
                {
                    val oldArticlesItem=categoryNewsResponse?.articles
                    val newArticlesItem=resultResponse.articles
                    oldArticlesItem?.addAll(newArticlesItem)
                }
                return Resource.Success(categoryNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun getSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
               searchNewsPageNumber++
                if(searchNewsResponse==null)
                {
                    searchNewsResponse=resultResponse
                }
                else
                {
                    val oldArticlesItem=searchNewsResponse?.articles
                    val newArticlesItem=resultResponse.articles
                    oldArticlesItem?.addAll(newArticlesItem)
                }
                return Resource.Success(searchNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun sendingCategory(msg: String) {
        message = msg
        message?.let { getCategoryNews("in",category =  it) }
    }
}