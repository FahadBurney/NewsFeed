package com.example.newsfeed.NewsApp.UI

import android.app.Application
import android.app.ProgressDialog
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsfeed.NewsApp.Database.ArticlesDao
import com.example.newsfeed.NewsApp.Database.ArticlesDatabase
import com.example.newsfeed.NewsApp.Database.ArticlesDatabase.Companion.getInstance
import com.example.newsfeed.NewsApp.Repository.NewsRepository
import com.example.newsfeed.NewsApp.api.RetrofitInstance
import com.example.newsfeed.NewsApp.models.ArticlesItem
import com.example.newsfeed.NewsApp.models.NewsResponse
import com.example.newsfeed.NewsApp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(application: Application?) : AndroidViewModel(application!!) {
    private val newsRepository: NewsRepository
    private val articlesDb: ArticlesDatabase?
    private val articlesDao: ArticlesDao?
    private var newsArticlesLiveData: MutableLiveData<List<ArticlesItem>?>? = null
    private var searchNewsLiveData: MutableLiveData<List<ArticlesItem>?>? = null
    private var Message: String? = null
    private var searchingText: String? = null
    private fun initNews(message: String) {
        val newsApi = RetrofitInstance.newsApi
        val call = newsApi.getBreakingNews("in", message, Constants.API_KEY)
        call!!.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful && response.body()!!.articles != null) {
                    val articlesItems = response.body()!!.articles
                    newsArticlesLiveData!!.postValue(articlesItems)
                } else {
                    Log.i(TAG, "onResponse but articles not successful")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : " + t.localizedMessage)
            }
        })
    }

    // liveData method to return back this method to Details Of News Fragment
    fun getBreakingNews(message: String): LiveData<List<ArticlesItem>?> {
        if (newsArticlesLiveData == null) {
            newsArticlesLiveData = MutableLiveData()
            Message = message
            //           Log.i("LiveData", "getBreakingNews " + message);
            // in the below function we pass the articles inside our liveData that we have
            // fetched from NewsApi
            initNews(message)
        } else if (message != Message) {
            newsArticlesLiveData = MutableLiveData()
            Message = message
            initNews(message)
        }
        return newsArticlesLiveData!!
    }

    fun SearchNews(searchQuery: String, progressDialog: ProgressDialog): LiveData<List<ArticlesItem>?> {
        if (searchNewsLiveData == null) {
            searchNewsLiveData = MutableLiveData()
            searchingText = searchQuery
            searchNewsFromApi(searchQuery, progressDialog)
        } else if (!searchQuery.equals(searchingText, ignoreCase = true)) {
            searchNewsLiveData = MutableLiveData()
            searchingText = searchQuery
            searchNewsFromApi(searchQuery, progressDialog)
        } else {
            progressDialog.dismiss()
        }
        return searchNewsLiveData!!
    }

    private fun searchNewsFromApi(searchQuery: String, progressDialog: ProgressDialog) {
        val newsApi = RetrofitInstance.newsApi
        val searchNewsPage = 1
        val call = newsApi.SearchNews(searchQuery, searchNewsPage, Constants.API_KEY)
        //   Log.i("ViewModel", searchQuery);
        call!!.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val code = response.code()
                val message = response.message()
                Log.i("errorCode", "$code code message $message")
                Log.e(TAG, "onResponse  : " + response.body()!!.totalResults)
                if (response.isSuccessful && response.body()!!.articles != null) {
                    val articlesItems = response.body()!!.articles
                    searchNewsLiveData!!.setValue(articlesItems)
                    Log.e(TAG1, "articles from searchNews : " + searchNewsLiveData!!.value.toString())
                } else {
                    Log.i(TAG, "onResponse in SearchNews but articles not successful")
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : " + t.localizedMessage)
                progressDialog.setCancelable(true)
            }
        })
    }

    // Functions for retrieving data from Database
    fun saveArticle(articlesItem: ArticlesItem) {
        Log.i("Inside ViewMODEL", articlesItem.toString())
        newsRepository.upsert(articlesItem)
    }

    val savedNews: LiveData<List<ArticlesItem?>?>?
        get() = newsRepository.savedNews

    fun deleteArticle(articlesItem: ArticlesItem?) {
        newsRepository.deleteArticle(articlesItem)
    }

    companion object {
        private const val TAG = "NewsViewModel.Articles"
        const val TAG1 = "Articles"
    }

    //public NewsViewModel()
    //{
    //}
    init {
        newsRepository = NewsRepository(application)
        articlesDb = getInstance(application!!)
        articlesDao = articlesDb!!.articlesDao
    }
}