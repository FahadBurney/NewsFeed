package com.example.newsfeed.NewsApp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.newsfeed.NewsApp.NewsActivity;
import com.example.newsfeed.NewsApp.UI.NewsViewModel;
import com.example.newsfeed.R;

public class TypeOfNewsFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
    return inflater.inflate(R.layout.fragment_type_of_news, container, false);
    }
   private NavController navController;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         navController= Navigation.findNavController(view);

     //   newsViewModel = ((NewsActivity) getActivity()).ViewModel;
        Button scienceButton = getActivity().findViewById(R.id.scienceButton);
        Button healthButton = getActivity().findViewById(R.id.healthButton);
        Button entertainmentButton = getActivity().findViewById(R.id.entertainmentButton);
        Button sportsButton = getActivity().findViewById(R.id.sportsButton);
        Button technologyButton = getActivity().findViewById(R.id.technologyButton);
        Button businessButton = getActivity().findViewById(R.id.businessButton);
        Button generalButton = getActivity().findViewById(R.id.generalButton);
        generalButton.setOnClickListener(this);
        scienceButton.setOnClickListener(this);
        healthButton.setOnClickListener(this);
        sportsButton.setOnClickListener(this);
        technologyButton.setOnClickListener(this);
        businessButton.setOnClickListener(this);
        entertainmentButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TypeOfNewsFragmentDirections.ActionTypeOfNewsFragmentToDetailsOfNewsFragment action;
        switch (v.getId()) {
            case R.id.healthButton:
                 action=TypeOfNewsFragmentDirections.
                        actionTypeOfNewsFragmentToDetailsOfNewsFragment();
                action.setMessage("health");
                navController.navigate(action);
                Log.i("TypesOfNewsFragment","health button is clicked");
                break;
            case R.id.sportsButton:

                 action=TypeOfNewsFragmentDirections.
                        actionTypeOfNewsFragmentToDetailsOfNewsFragment();
                action.setMessage("sports");

                navController.navigate(action);
                Log.i("TypesOfNewsFragment","sports button is clicked");

                break;
            case R.id.entertainmentButton:

                action=TypeOfNewsFragmentDirections.
                        actionTypeOfNewsFragmentToDetailsOfNewsFragment();
                action.setMessage("entertainment");
                navController.navigate(action);
                Log.i("TypesOfNewsFragment","entertainment button is clicked");

                break;
            case R.id.technologyButton:

                action=TypeOfNewsFragmentDirections.
                        actionTypeOfNewsFragmentToDetailsOfNewsFragment();
                action.setMessage("technology");
                navController.navigate(action);

                Log.i("TypesOfNewsFragment","technology button is clicked");

                break;
            case R.id.businessButton:
                action=TypeOfNewsFragmentDirections.
                    actionTypeOfNewsFragmentToDetailsOfNewsFragment();
                action.setMessage("business");
                navController.navigate(action);
                Log.i("TypesOfNewsFragment","business button is clicked");

                break;
            case R.id.scienceButton:
                action=TypeOfNewsFragmentDirections.
                        actionTypeOfNewsFragmentToDetailsOfNewsFragment();
                action.setMessage("science");
                navController.navigate(action);
                Log.i("TypesOfNewsFragment","health button is clicked");

                break;
            case R.id.generalButton:

                action=TypeOfNewsFragmentDirections.
                        actionTypeOfNewsFragmentToDetailsOfNewsFragment();
                action.setMessage("general");
                navController.navigate(action);
                break;
        }
    }
}
