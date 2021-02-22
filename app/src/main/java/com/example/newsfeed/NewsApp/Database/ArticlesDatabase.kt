package com.example.newsfeed.NewsApp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsfeed.NewsApp.models.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 2)
@TypeConverters(Converter::class)
abstract class ArticlesDatabase : RoomDatabase() {
    // this method actually we use to access database operationmethods
    //that are put in articlesDao interface and we do it in repository class
    abstract val articlesDao: ArticlesDao?

    companion object {
        @Volatile
        private var instance: ArticlesDatabase? = null
        @JvmStatic
        fun getInstance(context: Context): ArticlesDatabase? {
            if (instance == null) {
                synchronized(ArticlesDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext,
                                ArticlesDatabase::class.java,
                                "articles_database.db").fallbackToDestructiveMigration().build()
                    }
                }
            }
            return instance
        }
    }
}