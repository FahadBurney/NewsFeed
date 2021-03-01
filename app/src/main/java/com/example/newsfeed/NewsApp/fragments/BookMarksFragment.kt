package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.NewsApp.NewsActivity
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.NewsApp.adapters.NewsAdapter
import com.example.newsfeed.NewsApp.adapters.setOnItemClickListener
import com.example.newsfeed.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bookmarks.*

class BookMarksFragment : Fragment(R.layout.fragment_bookmarks) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setUpRecyclerView()
        newsAdapter.apply {
            setOnItemClickListener {
                val bundle = Bundle().apply {
                    putSerializable("articles", it)
                }
                findNavController().navigate(R.id.action_bookMarksFragment_to_articleFragment, bundle)
            }
        }
        // ItemTouchHelperCallBack  to Delete Articles
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                // TODO("Not yet implemented")
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // TODO("Not yet implemented")
                val position = viewHolder.adapterPosition
                val articlesItem = newsAdapter.asyncDiffer.currentList[position]
                viewModel.deleteArticles(articlesItem)
                Snackbar.make(view, "Article Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO") {
                        viewModel.savedArticle(articlesItem)
                    }
                    show()
                }
            }
        }
        // attaching ItemCallBack To RecyclerView
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(bookMarksRecyclerView)
        }


        //observe the changes in database in BookMarks Fragment
        viewModel.getSavedNewsToDisplay().observe(viewLifecycleOwner, Observer { articlesItem ->
            newsAdapter.asyncDiffer.submitList(articlesItem)
        })

    }
    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        bookMarksRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}