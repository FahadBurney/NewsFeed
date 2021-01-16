package com.example.newsfeed.NewsApp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.newsfeed.NewsApp.models.ArticlesItem;

@Database(entities = {ArticlesItem.class},version=1)
@TypeConverters(Converter.class)
public abstract class ArticlesDatabase extends RoomDatabase
{
   private static ArticlesDatabase instance;

   // this method actually we use to access database operationmethods
    //that are put in articlesDao interface and we do it in repository class
   public abstract ArticlesDao getArticlesDao();

public static synchronized ArticlesDatabase getInstance(Context context)
{
 if(instance==null)
 {
     instance= Room.databaseBuilder(context.getApplicationContext(),
             ArticlesDatabase.class,
             "articles_database.db").
             fallbackToDestructiveMigration().build();
 }
return instance;
}
}
