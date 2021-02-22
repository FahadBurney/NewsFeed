package com.example.newsfeed.NewsApp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.newsfeed.NewsApp.fragments.TypeOfNewsFragmentDirections.ActionTypeOfNewsFragmentToDetailsOfNewsFragment
import com.example.newsfeed.R

class TypeOfNewsFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_type_of_news, container, false)
    }

    private var navController: NavController? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val scienceButton: CardView = activity!!.findViewById(R.id.scienceButton)
        val healthButton: CardView = activity!!.findViewById(R.id.healthButton)
        val entertainmentButton: CardView = activity!!.findViewById(R.id.entertainmentButton)
        val sportsButton: CardView = activity!!.findViewById(R.id.sportsButton)
        val technologyButton: CardView = activity!!.findViewById(R.id.technologyButton)
        val businessButton: CardView = activity!!.findViewById(R.id.businessButton)
        scienceButton.setOnClickListener(this)
        healthButton.setOnClickListener(this)
        sportsButton.setOnClickListener(this)
        technologyButton.setOnClickListener(this)
        businessButton.setOnClickListener(this)
        entertainmentButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val action: ActionTypeOfNewsFragmentToDetailsOfNewsFragment
        when (v.id) {
            R.id.healthButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "health"
                navController!!.navigate(action)
            }
            R.id.sportsButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "sports"
                navController!!.navigate(action)
            }
            R.id.entertainmentButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "entertainment"
                navController!!.navigate(action)
            }
            R.id.technologyButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "technology"
                navController!!.navigate(action)
            }
            R.id.businessButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "business"
                navController!!.navigate(action)
            }
            R.id.scienceButton -> {
                action = TypeOfNewsFragmentDirections.actionTypeOfNewsFragmentToDetailsOfNewsFragment()
                action.message = "science"
                navController!!.navigate(action)
            }
        }
    }
}