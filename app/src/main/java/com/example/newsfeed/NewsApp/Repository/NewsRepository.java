package com.example.newsfeed.NewsApp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.newsfeed.NewsApp.Database.ArticlesDao;
import com.example.newsfeed.NewsApp.Database.ArticlesDatabase;
import com.example.newsfeed.NewsApp.models.ArticlesItem;

import java.util.Arrays;
import java.util.List;

public class NewsRepository {

    public ArticlesDatabase database;
    private ArticlesDao articlesDao;
    public NewsRepository(Application application) {
        this.database = ArticlesDatabase.getInstance(application);
    articlesDao=database.getArticlesDao();
    }

public void   upsert(ArticlesItem articlesItem)
    {
         new updateAndInsertAsyncTask(articlesDao).execute(articlesItem);
    }
 public    LiveData<List<ArticlesItem>> getSavedNews()
    {
        Log.i("articles",ArticlesItem.DiffUtilCallBack.toString()+"");
        return  articlesDao.getAllArticles();
    }
   public void deleteArticle(ArticlesItem articlesItem)
    {
    new DeleteAsyncTask(articlesDao).execute(articlesItem);
    }



    private abstract class OperationsAsyncTask extends AsyncTask<ArticlesItem, Void, Void> {
       ArticlesDao mAsyncTaskDao;
        OperationsAsyncTask(ArticlesDao dao) {
            this.mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(ArticlesItem... articlesItems) {
            return null;
        }

     //   protected abstract Void doInBackground(String url);
    }
    private class updateAndInsertAsyncTask extends OperationsAsyncTask
    {
updateAndInsertAsyncTask(ArticlesDao articlesDao)
{
    super(articlesDao);
}
        @Override
        protected Void doInBackground(ArticlesItem... articlesItems) {
            mAsyncTaskDao.upsert(articlesItems[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(ArticlesDao articlesDao) {
            super(articlesDao);
        }

        @Override
        protected Void doInBackground(ArticlesItem... articlesItem) {
            mAsyncTaskDao.deleteArticles(articlesItem[0]);
            return null;
        }
    }

    }

