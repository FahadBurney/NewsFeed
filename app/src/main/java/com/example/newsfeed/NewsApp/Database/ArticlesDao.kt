package com.example.newsfeed.NewsApp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsfeed.NewsApp.models.ArticlesItem;

import java.util.List;

@Dao
public interface ArticlesDao {
    //add articles in our database
    ArticlesItem articles = new ArticlesItem();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(ArticlesItem  articles);
    // get all articles in database so that we can show them in our bookmarks fragment
    @Query("Select * from articles")
    LiveData<List<ArticlesItem>> getAllArticles();

    // delete the articles
    @Delete
    void deleteArticles(ArticlesItem articles);
}
