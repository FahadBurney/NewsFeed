package com.example.newsfeed.NewsApp.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsfeed.NewsApp.models.ArticlesItem

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(articles: ArticlesItem):Long

    // get all articles in database so that we can show them in our bookmarks fragment
    @Query("Select * from articles")
    fun allArticles(): LiveData<List<ArticlesItem>>
    // delete the articles
    @Delete
suspend  fun deleteArticles(articles: ArticlesItem)


}