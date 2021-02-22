package com.example.newsfeed.NewsApp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.NewsApp.UI.NewsViewModel;
import com.example.newsfeed.NewsApp.adapters.NewsAdapter;
import com.example.newsfeed.NewsApp.models.ArticlesItem;
import com.example.newsfeed.R;

public class DetailsOfNewsFragment extends Fragment implements NewsAdapter.ArticleClickInterface {
    public DetailsOfNewsFragment() {

    }

    String message;
    private NewsAdapter newsAdapter;
    private NavController navController;
private NewsViewModel newsViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //  model = new ViewModelProvider(this).get(NewsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        setupRecyclerView(view);
        if (getArguments() != null) {
            DetailsOfNewsFragmentArgs args = DetailsOfNewsFragmentArgs.fromBundle(getArguments());
            message = args.getMessage();
        }
        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
 //       recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        newsAdapter = new NewsAdapter(ArticlesItem.DiffUtilCallBack, this);
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        // created instance of viewModel here
        // Basically we want to show updated items on the NewsAdapter List
        // Therefore, we actually trigger the liveData method to update the list
        //observe Method is used Basically to observe the List on UI or recyclerView Here
        NewsViewModel articleViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        articleViewModel.getBreakingNews(message).observe(getViewLifecycleOwner(), articlesItems -> newsAdapter.submitList(articlesItems));
        //newsAdapter.setOnItemClickListener(new V);
        // D
    }

    @Override
    public void openArticle(int position) {

        //here we get the article that we want to pass to Article Fragment
        ArticlesItem articles = newsAdapter.getCurrentList().get(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("article", articles);
        navController.navigate(R.id.action_detailsOfNewsFragment_to_articleFragment, bundle);
    }

/*


    }
*/
    
    }

