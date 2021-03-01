package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.NewsApp.NewsActivity
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.NewsApp.adapters.NewsAdapter
import com.example.newsfeed.NewsApp.adapters.setOnItemClickListener
import com.example.newsfeed.NewsApp.util.Constants.Companion.DELAY_TIME
import com.example.newsfeed.NewsApp.util.Resource
import com.example.newsfeed.R
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.paginationProgressBar
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
        newsAdapter.apply {
            setOnItemClickListener {
                val bundle = Bundle().apply {
                    putSerializable("articles", it)
                }
                findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment, bundle)
            }
        }
//Implemented TextWatcher with Coroutine In it, that's it
        var job: Job? = null
        search_bar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                job?.cancel()
                job = MainScope().launch {
                    delay(DELAY_TIME)
                    if (search_bar.text.isNotEmpty()) {
                        searchRecyclerView.visibility=View.VISIBLE
                        viewModel.getSearchNews(search_bar.text.toString())
                    }
                    else if(search_bar.text.isEmpty()){
searchRecyclerView.visibility=View.INVISIBLE
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
                        newsAdapter.asyncDiffer.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / 20 + 2
                        isLastPage = viewModel.searchNewsPageNumber == totalPages
                        if (isLastPage) {
                            searchRecyclerView.setPadding(0, 0, 0, 0)
                        }

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
        isLoading = false

    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {


        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 20

            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getSearchNews(search_bar.text.toString())
                isScrolling = false
            }


        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }

        }
    }


    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        searchRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchNewsFragment.scrollListener)

        }
    }

}


