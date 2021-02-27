package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.NewsApp.NewsActivity
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.NewsApp.adapters.NewsAdapter
import com.example.newsfeed.NewsApp.util.Constants.Companion.DELAY_TIME
import com.example.newsfeed.NewsApp.util.Resource
import com.example.newsfeed.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.coroutines.*
import androidx.core.widget.addTextChangedListener as addTextChangedListener1

class SearchNewsFragment : Fragment(R.layout.fragment_search) {
    lateinit var viewModel: NewsViewModel
    lateinit var navController: NavController
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        navController = Navigation.findNavController(view)
        setUpRecyclerView()
//Implemented TextWatcher with Coroutine In it, that's it
        var job:Job?=null
search_bar.addTextChangedListener(object :TextWatcher{
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        job?.cancel()
        job= MainScope().launch {
            delay(DELAY_TIME)
            if(search_bar.text.isNotEmpty())
            {
                viewModel.getSearchNews(search_bar.text.toString())
            }
        }

    }

    override fun afterTextChanged(s: Editable?) {
    }
})

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.asyncDiffer.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("Tag", "Error Occured while getting Response $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        searchRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}


