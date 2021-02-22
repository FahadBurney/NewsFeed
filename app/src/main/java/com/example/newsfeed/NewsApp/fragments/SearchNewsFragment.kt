package com.example.newsfeed.NewsApp.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.NewsApp.adapters.NewsAdapter
import com.example.newsfeed.NewsApp.adapters.NewsAdapter.ArticleClickInterface
import com.example.newsfeed.NewsApp.models.ArticlesItem
import com.example.newsfeed.R

class SearchNewsFragment : Fragment(), ArticleClickInterface {
    private var newsAdapter: NewsAdapter? = null

    //  private Timer timer;
    //  private String savedSearchQuery;
    private var navController: NavController? = null

    //  private NewsViewModel newsViewModel;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        setupRecyclerView(view)
        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.RecyclerView)
        //     recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        newsAdapter = NewsAdapter(ArticlesItem.DiffUtilCallBack, this)
        recyclerView.adapter = newsAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        navController = Navigation.findNavController(view)
        val searchQuery = activity!!.findViewById<EditText>(R.id.search_bar)
        // searchQuery.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        val search = searchQuery.text.toString()
        //  Log.i("Query",search);
        if (!search.isEmpty()) {
            // displaying a progress dialog
            val progressDialog = ProgressDialog(getView()!!.context)
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setMessage("Loading..")
            Log.i("Query", "$search inside")
            articleViewModel.SearchNews(search, progressDialog).observe(viewLifecycleOwner, { articlesItems: List<ArticlesItem?> -> newsAdapter!!.submitList(articlesItems) })
        }
    }

    override fun openArticle(position: Int) {
        val articles = newsAdapter!!.currentList[position]!!
        val bundle = Bundle()
        bundle.putSerializable("article", articles)
        navController!!.navigate(R.id.action_searchNewsFragment_to_articleFragment, bundle)
    }
}