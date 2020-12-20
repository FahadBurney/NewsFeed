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
public interface ArticlesDao
{
@Insert(onConflict= OnConflictStrategy.REPLACE)
    Long upsert(ArticlesItem articles);

@Query("Select * from articles")
    LiveData<List<ArticlesItem>> getAllArticles();
@Delete
    void deleteArticles(ArticlesItem articles);


}
