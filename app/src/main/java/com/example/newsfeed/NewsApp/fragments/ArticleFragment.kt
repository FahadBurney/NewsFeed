package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsfeed.NewsApp.NewsActivity
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ArticleFragment : Fragment() {
    lateinit var viewModel: NewsViewModel

    lateinit var newsViewModel: NewsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel


    }
}