package com.example.newsfeed.NewsApp.api

import com.example.newsfeed.NewsApp.util.Constants
import com.example.newsfeed.NewsApp.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {

            // only logging responses,Create a logger
            val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            // we use this interceptor to create network clients
            val client = OkHttpClient.Builder().addInterceptor(logging)
            // now we use our client and pass it to our retrofit instance
            // addConverterFactory->how our response to be  interpreted and converted to Kotlin Objects
            //GsonConverterFactory->Google Implementation of  Json Converting
            val builder = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
            //creating a retrofit instance
           builder.build()
        }
        // this is actual api object that we will use to make our actual network requests
val api: NewsApi by lazy {
retrofit.create(NewsApi::class.java)
        }

    }


}