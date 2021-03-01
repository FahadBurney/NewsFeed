package com.example.newsfeed.NewsApp.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "articles", indices = [Index(value = ["url"], unique = true)])

data class ArticlesItem(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = 0,
        val author: String?=null,
        val content: String?,
        val description: String?,
        val publishedAt: String?,
        val source: Source?,
        val title: String?,
        val url: String?,
        val urlToImage: String?
):Serializable