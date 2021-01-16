package com.example.newsfeed.NewsApp.api;

import android.util.Log;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.newsfeed.NewsApp.models.ArticlesItem;
import com.example.newsfeed.NewsApp.models.NewsResponse;
import com.example.newsfeed.NewsApp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static okhttp3.logging.HttpLoggingInterceptor.*;

public class RetrofitInstance {

    private List<ArticlesItem> articles = new ArrayList<>();
    private static final String TAG = "RetroInst.onResponse";
    public static final String TAG1 = "Articles";

    // only logging responses
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(Level.BODY);
// we use this interceptor to create network clients
    private static OkHttpClient.Builder client= new OkHttpClient.Builder();
    {
        if (!client.interceptors().contains(logging)) {
            client.addInterceptor(logging);
        }
    }
    // now we use our client and pass it to our retrofit instance
// addConverterFactory->how our response to be  interpreted and converted to Java Objects
    //GsonConverterFactory->Google Implementation of  Json Converting
     private static final Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build());

    private static final Retrofit retrofit = builder.build();
// this is actual api object that we will use to make our actual network requests
  public static NewsApi newsApi = retrofit.create(NewsApi.class);

}
