package com.example.newsfeed.NewsApp.api

import com.example.newsfeed.NewsApp.util.Constants
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        // only logging responses
        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        // we use this interceptor to create network clients
        private val client: Builder = Builder()

        // now we use our client and pass it to our retrofit instance
        // addConverterFactory->how our response to be  interpreted and converted to Java Objects
        //GsonConverterFactory->Google Implementation of  Json Converting
        private val builder = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
        private val retrofit = builder.build()

        // this is actual api object that we will use to make our actual network requests
        @JvmField
        var newsApi = retrofit.create(NewsApi::class.java)
    }

    init {
        if (!client.interceptors().contains(logging)) {
            client.addInterceptor(logging)
        }
    }
}