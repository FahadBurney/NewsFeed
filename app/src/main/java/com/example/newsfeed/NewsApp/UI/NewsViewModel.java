package com.example.newsfeed.NewsApp.UI;

import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsfeed.NewsApp.NewsActivity;
import com.example.newsfeed.NewsApp.Repository.NewsRepository;
import com.example.newsfeed.NewsApp.api.NewsApi;
import com.example.newsfeed.NewsApp.api.RetrofitInstance;
import com.example.newsfeed.NewsApp.models.ArticlesItem;
import com.example.newsfeed.NewsApp.models.NewsResponse;
import com.example.newsfeed.NewsApp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {
    public NewsRepository newsRepository;

    public NewsViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public NewsViewModel() {
        // zero-args constructor
    }

    private MutableLiveData<List<ArticlesItem>> newsArticlesLiveData;
    private MutableLiveData<List<ArticlesItem>> searchNewsLiveData;

    private static final String TAG = "NewsViewModel.Articles";
private String Message;

    public LiveData<List<ArticlesItem>> getBreakingNews(String message) {

        if (newsArticlesLiveData == null) {
            newsArticlesLiveData = new MutableLiveData<>();
            this.Message=message;
            Log.i("LiveData","getBreakingNews "+message);
            initNews(message);
        }
        else if(!message.equals(Message))
        {
            newsArticlesLiveData = new MutableLiveData<>();
           this.Message=message;
            initNews(message);
        }
        return newsArticlesLiveData;
    }

    private void initNews(String message) {
        NewsApi newsApi = RetrofitInstance.newsApi;
     //   Log.i("ArticleViewModel",message+" ");
        Call<NewsResponse> call = newsApi.getBreakingNews("in",message, Constants.API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    List<ArticlesItem> articlesItems = response.body().getArticles();
                    newsArticlesLiveData.postValue(articlesItems);
                } else {
                    Log.i(TAG, "onResponse but articles not successful");
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure : " + t.getLocalizedMessage());
            }
        });
    }


}
