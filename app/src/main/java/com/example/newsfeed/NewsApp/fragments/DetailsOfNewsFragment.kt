package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.NewsApp.NewsActivity
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.NewsApp.adapters.NewsAdapter
import com.example.newsfeed.NewsApp.util.Resource
import com.example.newsfeed.R
import kotlinx.android.synthetic.main.article_item_display.*
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsOfNewsFragment : Fragment(R.layout.fragment_details)
{
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var message: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setUpRecyclerView()
        arguments?.let {
            val args = DetailsOfNewsFragmentArgs.fromBundle(it)
            message=args.message
            Log.d("message passed",message)
        }


        viewModel.sendingCategory(message)
        viewModel.categoryNews.observe(viewLifecycleOwner, Observer{
            response ->
            when (response)
            {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsResponse ->
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
        detailsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}