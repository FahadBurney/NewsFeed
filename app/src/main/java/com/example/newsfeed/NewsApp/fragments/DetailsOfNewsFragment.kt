package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class DetailsOfNewsFragment : Fragment(), ArticleClickInterface {
    var message: String? = null
    private var newsAdapter: NewsAdapter? = null
    private var navController: NavController? = null
    private val newsViewModel: NewsViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //  model = new ViewModelProvider(this).get(NewsViewModel.class);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        setupRecyclerView(view)
        if (arguments != null) {
            val args = DetailsOfNewsFragmentArgs.fromBundle(arguments!!)
            message = args.message
        }
        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.RecyclerView)
        //       recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        newsAdapter = NewsAdapter(ArticlesItem.DiffUtilCallBack, this)
        recyclerView.adapter = newsAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        // created instance of viewModel here
        // Basically we want to show updated items on the NewsAdapter List
        // Therefore, we actually trigger the liveData method to update the list
        //observe Method is used Basically to observe the List on UI or recyclerView Here
        val articleViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        articleViewModel.getBreakingNews(message).observe(viewLifecycleOwner, { articlesItems: List<ArticlesItem?> -> newsAdapter!!.submitList(articlesItems) })
        //newsAdapter.setOnItemClickListener(new V);
        // D
    }

    override fun openArticle(position: Int) {

        //here we get the article that we want to pass to Article Fragment
        val articles = newsAdapter!!.currentList[position]!!
        val bundle = Bundle()
        bundle.putSerializable("article", articles)
        navController!!.navigate(R.id.action_detailsOfNewsFragment_to_articleFragment, bundle)
    } /*


    }
*/
}