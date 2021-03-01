package com.example.newsfeed.NewsApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.newsfeed.NewsApp.Database.ArticlesDatabase
import com.example.newsfeed.NewsApp.Repository.NewsRepository
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.NewsApp.UI.NewsViewModelProviderFactory
import com.example.newsfeed.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        //here we actually make declarations of newsViewModel and NewsRepository and NewsViewModelProviderFactory
        val newsRepository=NewsRepository(ArticlesDatabase.invoke(this))
        val newsViewModelProviderFactory=NewsViewModelProviderFactory(application,newsRepository)
        viewModel=ViewModelProvider(this, newsViewModelProviderFactory).get(NewsViewModel::class.java)

        // Here We actually set up bottom Navigation View For switching from one fragment to another
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

        //    NewsRepository newsRepository = new NewsRepository(ArticlesDatabase.getInstance(this));
        //      NewsViewModelProviderFactory ViewModelProvideFactory=new NewsViewModelProviderFactory(newsRepository);
//ViewModel= new ViewModelProvider(this,ViewModelProvideFactory).get(NewsViewModel.class);
      //  val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
       // val navController = Navigation.findNavController(this, R.id.newsNavHostFragment)
       // NavigationUI.setupWithNavController(bottomNavigationView, navController)


    }
}