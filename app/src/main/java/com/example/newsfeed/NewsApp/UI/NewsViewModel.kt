package com.example.newsfeed.NewsApp.UI

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.NewsApp.NewsApplication
import com.example.newsfeed.NewsApp.Repository.NewsRepository
import com.example.newsfeed.NewsApp.models.ArticlesItem
import com.example.newsfeed.NewsApp.models.NewsResponse
import com.example.newsfeed.NewsApp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class NewsViewModel(app: Application, val newsRepository: NewsRepository) : AndroidViewModel(app) {

    val categoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var categoryNewPageNumber = 1
    var categoryNewsResponse: NewsResponse? = null


    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPageNumber = 1
    var message: String? = null
    var searchNewsResponse: NewsResponse? = null

    var newCategory:String?=null
    var oldCategory:String?=null
    var newSearchQuery:String? = null
    var oldSearchQuery:String? = null

    fun getCategoryNews(countryCode: String, category: String) = viewModelScope.launch {
      //  categoryNews.postValue(Resource.Loading())
safeCategoryNewsCall(countryCode, category)
    }

    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
      //  Log.d("SearchText","query in getSearchNews() $searchQuery")

safeSearchNewsCall(searchQuery)
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
            response.body()?.let { resultResponse ->
                categoryNewPageNumber++
                if (categoryNewsResponse == null|| oldCategory!=newCategory) {
                    categoryNewPageNumber=1
                    oldCategory=newCategory
                    categoryNewsResponse = resultResponse
                } else {
                    categoryNewPageNumber++
                    val oldArticlesItem = categoryNewsResponse?.articles
                    val newArticlesItem = resultResponse.articles
                    oldArticlesItem?.addAll(newArticlesItem)
                }
                return Resource.Success(categoryNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun getSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {resultResponse->
                searchNewsPageNumber++
                if(searchNewsResponse == null || newSearchQuery != oldSearchQuery) {
                    searchNewsPageNumber = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                } else {
                    searchNewsPageNumber++
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }

                return Resource.Success (searchNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun sendingCategory(msg: String) {
        message = msg
        message?.let { getCategoryNews("in", category = it) }
    }

    private suspend fun safeCategoryNewsCall(countryCode: String,category: String){
        newCategory=category
        categoryNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepository.getCategoryNews(countryCode, category, categoryNewPageNumber)
                categoryNews.postValue(getCategoryNewsResponse(response))
            }
            else{
categoryNews.postValue(Resource.Error("No Internet Connection"))
            }
            }
        catch (t:Throwable){
when(t){
    is IOException->categoryNews.postValue(Resource.Error("Network Failure"))
    else ->categoryNews.postValue(Resource.Error("Conversion Error"))
}
        }
    }
    private suspend fun safeSearchNewsCall(searchQuery: String){
        newSearchQuery=searchQuery
        searchNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                Log.d("SearchText","safeSearchNewsCall inside try inside if $searchQuery")
                val response = newsRepository.searchNews(searchQuery,searchNewsPageNumber)
                searchNews.postValue(getSearchNewsResponse(response))
            }
            else{
                Log.d("SearchText","safeSearchNewsCall inside try inside else $searchQuery")

                searchNews.postValue(Resource.Error("No Internet Connection"))
            }
        }
        catch (t:Throwable){
            when(t){
                is IOException->searchNews.postValue(Resource.Error("Network Failure"))
                else ->searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                    ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}