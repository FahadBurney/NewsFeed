package com.example.newsfeed.NewsApp.api;

import com.example.newsfeed.NewsApp.util.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level;

public class RetrofitInstance {


    // only logging responses
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(Level.BODY);
// we use this interceptor to create network clients
    private static final OkHttpClient.Builder client= new OkHttpClient.Builder();
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
