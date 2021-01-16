package com.example.newsfeed.NewsApp.Repository;

import com.example.newsfeed.NewsApp.Database.ArticlesDatabase;
import com.example.newsfeed.NewsApp.api.NewsApi;
import com.example.newsfeed.NewsApp.api.RetrofitInstance;
import com.example.newsfeed.NewsApp.util.Constants;

public class NewsRepository {

    public ArticlesDatabase database;
    public NewsRepository(ArticlesDatabase database) {
        this.database = database;
    }

}
