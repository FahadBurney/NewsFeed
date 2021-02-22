package com.example.newsfeed.NewsApp.util

abstract class Resource<T>(private val data: T, private val message: String?) {
    class Success<T>(message: String?, private val data: T) : Resource<T>(data, message)
    class Error<T>(private val message: String?, private val data: T) : Resource<T>(data, message)
    class Loading<T>(data: T, message: String?) : Resource<T>(data, message)
}