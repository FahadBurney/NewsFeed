package com.example.newsfeed.NewsApp.UI;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsfeed.NewsApp.Repository.NewsRepository;


public class NewsViewModelProviderFactory implements ViewModelProvider.Factory {


    private NewsRepository NewsRepository;

    public NewsViewModelProviderFactory(NewsRepository newsRepository) {
        NewsRepository = newsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
return (T) new NewsViewModel(NewsRepository);
    }
}
