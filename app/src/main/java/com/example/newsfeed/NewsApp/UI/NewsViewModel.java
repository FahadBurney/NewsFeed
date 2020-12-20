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

    private MutableLiveData<List<ArticlesItem>> articlesLiveData;
    private static final String TAG = "NewsViewModel.Articles";
    public static final String TAG1 = "Articles";


    public LiveData<List<ArticlesItem>> getBreakingNews(String message) {
        if (articlesLiveData == null) {
            articlesLiveData = new MutableLiveData<>();
            initNews(message);
        }
        return articlesLiveData;
    }

    private void initNews(String message) {
        NewsApi newsApi=RetrofitInstance.newsApi;
        Call<NewsResponse> call = newsApi.getBreakingNews(message, Constants.API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                Log.e(TAG, "onResponse  : " + response.body().getTotalResults());
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    List<ArticlesItem> articlesItems=response.body().getArticles();
                    articlesLiveData.setValue(articlesItems);
                    Log.e(TAG1, "articles : " + articlesLiveData.getValue().toString());
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
