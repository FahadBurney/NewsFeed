package com.example.newsfeed.NewsApp.Database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsfeed.NewsApp.models.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class ArticlesDatabase : RoomDatabase() {
    // this method actually we use to access database operationmethods
    //that are put in articlesDao interface and we do it in repository class
abstract fun getArticlesDao():ArticlesDao
    companion object {
//other threads can easily when you change the instance
        @Volatile
        private var instance: ArticlesDatabase? = null

/*
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
*/
        private val LOCK=Any()
        operator fun invoke(context: Context)= instance?: synchronized(LOCK)
        {
            instance?:createDatabase(context).also{
               instance=it
            Log.d("TAG","Context is $it")
            }
        }

        private fun createDatabase(context: Context)=Room.databaseBuilder(context.applicationContext,ArticlesDatabase::class.java,
                                    "articles_database.db").fallbackToDestructiveMigration().build()

        }

    }
