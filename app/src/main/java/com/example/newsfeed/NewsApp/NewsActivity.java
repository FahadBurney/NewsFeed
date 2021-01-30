package com.example.newsfeed.NewsApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.newsfeed.NewsApp.UI.NewsViewModel;
import com.example.newsfeed.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewsActivity extends AppCompatActivity {
    public NewsViewModel ViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

    //    NewsRepository newsRepository = new NewsRepository(ArticlesDatabase.getInstance(this));
  //      NewsViewModelProviderFactory ViewModelProvideFactory=new NewsViewModelProviderFactory(newsRepository);
//ViewModel= new ViewModelProvider(this,ViewModelProvideFactory).get(NewsViewModel.class);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        NavController navController= Navigation.findNavController(this,R.id.newsNavHostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}