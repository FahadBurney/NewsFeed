package com.example.newsfeed.NewsApp.Repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.newsfeed.NewsApp.Database.ArticlesDao
import com.example.newsfeed.NewsApp.Database.ArticlesDatabase
import com.example.newsfeed.NewsApp.Database.ArticlesDatabase.Companion.getInstance
import com.example.newsfeed.NewsApp.models.ArticlesItem

class NewsRepository(application: Application?) {
    var database: ArticlesDatabase?
    private val articlesDao: ArticlesDao?
    fun upsert(articlesItem: ArticlesItem?) {
        updateAndInsertAsyncTask(articlesDao).execute(articlesItem)
    }

    val savedNews: LiveData<List<ArticlesItem?>?>?
        get() {
            Log.i("articles", ArticlesItem.DiffUtilCallBack.toString() + "")
            return articlesDao!!.allArticles
        }

    fun deleteArticle(articlesItem: ArticlesItem?) {
        DeleteAsyncTask(articlesDao).execute(articlesItem)
    }

    private abstract inner class OperationsAsyncTask internal constructor(var mAsyncTaskDao: ArticlesDao?) : AsyncTask<ArticlesItem?, Void?, Void?>() {
        protected override fun doInBackground(vararg articlesItems: ArticlesItem): Void? {
            return null
        } //   protected abstract Void doInBackground(String url);
    }

    private inner class updateAndInsertAsyncTask internal constructor(articlesDao: ArticlesDao?) : OperationsAsyncTask(articlesDao) {
        override fun doInBackground(vararg articlesItems: ArticlesItem): Void? {
            mAsyncTaskDao!!.upsert(articlesItems[0])
            return null
        }
    }

    private inner class DeleteAsyncTask(articlesDao: ArticlesDao?) : OperationsAsyncTask(articlesDao) {
        override fun doInBackground(vararg articlesItem: ArticlesItem): Void? {
            mAsyncTaskDao!!.deleteArticles(articlesItem[0])
            return null
        }
    }

    init {
        database = getInstance(application!!)
        articlesDao = database!!.articlesDao
    }
}