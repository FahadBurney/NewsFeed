package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.NewsApp.adapters.NewsAdapter
import com.example.newsfeed.NewsApp.adapters.NewsAdapter.ArticleClickInterface
import com.example.newsfeed.NewsApp.models.ArticlesItem
import com.example.newsfeed.R
import com.google.android.material.snackbar.Snackbar

class BookMarksFragment : Fragment(), ArticleClickInterface {
    private var newsViewModel: NewsViewModel? = null
    private var newsAdapter: NewsAdapter? = null
    private var navController: NavController? = null
    private var recyclerView: RecyclerView? = null
    var simpleCallback: ItemTouchHelper.SimpleCallback? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.RecyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(view.context))
        newsAdapter = NewsAdapter(ArticlesItem.DiffUtilCallBack, this)
        recyclerView.setAdapter(newsAdapter)
        //     recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        setupRecyclerView(view)
        // now we will call the liveData method that shows our articles that are saved in database
        // inside our recycler view
        newsViewModel!!.savedNews.observe(viewLifecycleOwner, { articlesItems: List<ArticlesItem?> -> newsAdapter!!.submitList(articlesItems) })
        navController = Navigation.findNavController(view)
        simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    val position = viewHolder.adapterPosition
                    val articlesItem = newsAdapter!!.currentList[position]!!
                    newsViewModel!!.deleteArticle(articlesItem)
                    Log.i("I got deleted", articlesItem.toString() + "")
                    Snackbar.make(view, "Successfully Deleted Article", Snackbar.LENGTH_LONG).setAction("Undo") { v: View? -> newsViewModel!!.saveArticle(articlesItem) }.show()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun openArticle(position: Int) {
        //here we get the article that we want to pass to Article Fragment
        val articles = newsAdapter!!.currentList[position]!!
        val bundle = Bundle()
        bundle.putSerializable("article", articles)
        navController!!.navigate(R.id.action_bookMarksFragment_to_articleFragment, bundle)
    }
}