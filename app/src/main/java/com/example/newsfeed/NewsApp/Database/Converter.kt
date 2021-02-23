package com.example.newsfeed.NewsApp.Database

import androidx.room.TypeConverter
import com.example.newsfeed.NewsApp.models.Source

class Converter {
    // passing source as a parameter and String is return-type
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }
    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name,name)
    }
}