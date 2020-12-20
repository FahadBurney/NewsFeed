package com.example.newsfeed.NewsApp.api;

import com.example.newsfeed.NewsApp.models.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("v2/top-headlines")
    Call<NewsResponse> getBreakingNews( @Query(value = "category") String category,
                                           @Query("apiKey") String apiKey);

    @GET("v2/everything")
    Call<NewsResponse> SearchNews(@Query("q") String searchQuery,
                                  @Query("page") int pageNumber,
                                  @Query("apiKey") String apiKey);
}
