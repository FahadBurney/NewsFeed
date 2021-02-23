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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    private var newsViewModel: NewsViewModel? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = ArticleFragmentArgs.fromBundle(arguments!!)
        viewModel=(activity as NewsActivity).viewModel

        //displaying them in webView
        val webView = getView()!!.findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(args.article.url)
        val fab: FloatingActionButton = getView()!!.findViewById(R.id.BookMarksButton)
        newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        fab.setOnClickListener {
            newsViewModel!!.saveArticle(args.article)
            Toast.makeText(context, "BookMarked", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val TAG = "About Articles"
    }
}