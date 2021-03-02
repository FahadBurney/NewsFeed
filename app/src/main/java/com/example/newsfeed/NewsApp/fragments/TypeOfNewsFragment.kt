package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.newsfeed.NewsApp.NewsActivity
import com.example.newsfeed.NewsApp.UI.NewsViewModel
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
        val bundle=Bundle().apply{
            when(v)
            {
                healthButton->{
                    putString("message","health")
                    findNavController().navigate(R.id.action_typeOfNewsFragment_to_detailsOfNewsFragment,this)
                }
                sportsButton->{
                    putString("message","sports")
                    findNavController().navigate(R.id.action_typeOfNewsFragment_to_detailsOfNewsFragment,this)
                }
                technologyButton->{
                    putString("message","technology")
                    findNavController().navigate(R.id.action_typeOfNewsFragment_to_detailsOfNewsFragment,this)
                }
                businessButton->{
                    putString("message","business")
                    findNavController().navigate(R.id.action_typeOfNewsFragment_to_detailsOfNewsFragment,this)
                }
                entertainmentButton->{
                    putString("message","entertainment")
                    findNavController().navigate(R.id.action_typeOfNewsFragment_to_detailsOfNewsFragment,this)
                }
                scienceButton->{
                    putString("message","science")
                    findNavController().navigate(R.id.action_typeOfNewsFragment_to_detailsOfNewsFragment,this)
                }
            }
        }

    }
}