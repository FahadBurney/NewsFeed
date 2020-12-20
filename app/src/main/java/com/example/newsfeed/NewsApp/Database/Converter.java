package com.example.newsfeed.NewsApp.Database;

import androidx.room.TypeConverter;

import com.example.newsfeed.NewsApp.models.Source;

public class Converter {

    // passing source as a parameter and String is return-type
    @TypeConverter
    public String fromSource(Source source)
    {
return source.getName();
    }
    @TypeConverter
    public Source toSource(String name)
    {
        return new Source(name);
    }

}
