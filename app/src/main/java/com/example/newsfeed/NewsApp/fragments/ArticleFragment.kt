package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsfeed.NewsApp.NewsActivity
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.R
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {
    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        val articlesItem = args.articles
        Log.d("Articles", "articles in articleFragment is $articlesItem")
        webView.apply {
            webViewClient = WebViewClient()
            articlesItem.url?.let { loadUrl(it) }
        }
// Code for Floating Action Button
        BookMarksButton.setOnClickListener {
            viewModel.savedArticle(articlesItem)
       Toast.makeText(context,"Article Saved Successfully",Toast.LENGTH_SHORT).show()
        }
    }
}