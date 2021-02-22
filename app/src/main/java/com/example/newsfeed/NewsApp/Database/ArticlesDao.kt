package com.example.newsfeed.NewsApp.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsfeed.NewsApp.models.ArticlesItem

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(articles: ArticlesItem?)

    // get all articles in database so that we can show them in our bookmarks fragment
    @get:Query("Select * from articles")
    val allArticles: LiveData<List<ArticlesItem?>?>?

    // delete the articles
    @Delete
    fun deleteArticles(articles: ArticlesItem?)

    companion object {
        //add articles in our database
        val articles = ArticlesItem()
    }
}