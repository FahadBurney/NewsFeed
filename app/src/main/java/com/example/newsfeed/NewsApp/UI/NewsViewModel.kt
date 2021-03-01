package com.example.newsfeed.NewsApp.UI

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
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

    fun getCategoryNews(countryCode: String, category: String) = viewModelScope.launch {
      //  categoryNews.postValue(Resource.Loading())
safeCategoryNewsCall(countryCode, category)
    }

    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
        //  val categoryNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
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
                if (categoryNewsResponse == null) {
                    categoryNewsResponse = resultResponse
                } else {
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
            response.body()?.let { resultResponse ->
                searchNewsPageNumber++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticlesItem = searchNewsResponse?.articles
                    val newArticlesItem = resultResponse.articles
                    oldArticlesItem?.addAll(newArticlesItem)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun sendingCategory(msg: String) {
        message = msg
        message?.let { getCategoryNews("in", category = it) }
    }

    private suspend fun safeCategoryNewsCall(countryCode: String,category: String){
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
        searchNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepository.searchNews(searchQuery,searchNewsPageNumber)
                searchNews.postValue(getSearchNewsResponse(response))
            }
            else{
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