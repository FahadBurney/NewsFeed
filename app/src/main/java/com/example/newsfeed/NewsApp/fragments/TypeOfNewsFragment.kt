package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.newsfeed.NewsApp.NewsActivity
import com.example.newsfeed.NewsApp.UI.NewsViewModel
import com.example.newsfeed.NewsApp.fragments.TypeOfNewsFragmentDirections.ActionTypeOfNewsFragmentToDetailsOfNewsFragment
import com.example.newsfeed.R
import kotlinx.android.synthetic.main.fragment_type_of_news.*

class TypeOfNewsFragment : Fragment(R.layout.fragment_type_of_news), View.OnClickListener {
    lateinit var viewModel: NewsViewModel

    lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        navController = Navigation.findNavController(view)
        scienceButton.setOnClickListener(this)
        healthButton.setOnClickListener(this)
        sportsButton.setOnClickListener(this)
        technologyButton.setOnClickListener(this)
        businessButton.setOnClickListener(this)
        entertainmentButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val action: ActionTypeOfNewsFragmentToDetailsOfNewsFragment
        when (v) {
            healthButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "health"
                navController.navigate(action)
            }
            sportsButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "sports"
                navController.navigate(action)
            }
            entertainmentButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "entertainment"
                navController.navigate(action)
            }
            technologyButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "technology"
                navController.navigate(action)
            }
            businessButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "business"
                navController.navigate(action)
            }
            scienceButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "science"
                navController.navigate(action)
            }
        }
    }
}