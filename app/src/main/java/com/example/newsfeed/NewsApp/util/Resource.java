package com.example.newsfeed.NewsApp.util;

public abstract class Resource<T> {
    private final T data;
    private final String message;

    public Resource(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static final class Success<T> extends Resource<T>{
private T data;

        public Success( String message, T data) {
              super(data, message);
            this.data = data;
        }
    }
    public static final class Error<T> extends Resource<T>{
        private String message;
        private T data;
        public Error( String message, T data) {
            super(data, message);
            this.message = message;
            this.data=data;
        }
    }
    public static final class Loading<T> extends Resource<T>
    {
        public Loading(T data, String message) {
            super(data, message);
        }
    }




}