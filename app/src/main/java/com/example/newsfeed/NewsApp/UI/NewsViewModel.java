package com.example.newsfeed.NewsApp.UI;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ProgressBar;

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

public class
NewsViewModel extends ViewModel {
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
    public static final String TAG1 = "Articles";
    private String Message;
private String searchingText;
    private void initNews(String message) {
        NewsApi newsApi = RetrofitInstance.newsApi;
        Call<NewsResponse> call = newsApi.getBreakingNews("in", message, Constants.API_KEY);
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
    // liveData method
    public LiveData<List<ArticlesItem>> getBreakingNews(String message) {

        if (newsArticlesLiveData == null) {
            newsArticlesLiveData = new MutableLiveData<>();
            this.Message = message;
            Log.i("LiveData", "getBreakingNews " + message);
            // in the below function we pass the articles inside our liveData that we have
            // fetched from NewsApi
            initNews(message);
        } else if (!message.equals(Message)) {
            newsArticlesLiveData = new MutableLiveData<>();
            this.Message = message;
            initNews(message);
        }
        return newsArticlesLiveData;
    }
    public LiveData<List<ArticlesItem>> SearchNews(String searchQuery, ProgressDialog progressDialog) {
        if (searchNewsLiveData == null) {
            searchNewsLiveData = new MutableLiveData<>();
            searchingText=searchQuery;
            searchNewsFromApi(searchQuery,progressDialog);
        }
        else if(!searchQuery.equalsIgnoreCase(searchingText))
        {
            searchNewsLiveData=new MutableLiveData<>();
            searchingText=searchQuery;
            searchNewsFromApi(searchQuery,progressDialog);
        }else {
            progressDialog.dismiss();
        }return searchNewsLiveData;
    }
    private void searchNewsFromApi(String  searchQuery, ProgressDialog progressDialog) {
        NewsApi newsApi=RetrofitInstance.newsApi;
        int searchNewsPage = 1;
        Call<NewsResponse> call = newsApi.SearchNews(searchQuery, searchNewsPage,Constants.API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                Log.e(TAG, "onResponse  : " + response.body().getTotalResults());
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    List<ArticlesItem> articlesItems=response.body().getArticles();
                    searchNewsLiveData.setValue(articlesItems);
                    Log.e(TAG1, "articles from searchNews : " + searchNewsLiveData.getValue().toString());
                } else {
                    Log.i(TAG, "onResponse in SearchNews but articles not successful");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure : " + t.getLocalizedMessage());
                progressDialog.setCancelable(true);
            }
        });
    }


}