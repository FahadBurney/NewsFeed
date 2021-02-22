package com.example.newsfeed.NewsApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.newsfeed.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


        // Here We actually set up bottom Navigation View For switching from one fragment to another
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = Navigation.findNavController(this, R.id.newsNavHostFragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    bottomNavigationView.setUpWithNavController(newsNavHostFragment.findNavController())
    }
}