package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.newsfeed.NewsApp.NewsActivity
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.R

class BookMarksFragment : Fragment(R.layout.fragment_bookmarks) {

    lateinit var viewModel: NewsViewModel
    lateinit var navController: NavController



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
viewModel=(activity as NewsActivity).viewModel



}}