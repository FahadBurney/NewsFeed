package com.example.newsfeed.NewsApp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.NewsApp.UI.NewsViewModel;
import com.example.newsfeed.NewsApp.adapters.NewsAdapter;
import com.example.newsfeed.NewsApp.models.ArticlesItem;
import com.example.newsfeed.R;

import java.util.List;

public class SearchNewsFragment extends Fragment implements NewsAdapter.ArticleClickInterface {
    private NewsAdapter newsAdapter;
    //  private Timer timer;
    private String savedSearchQuery;
    private NavController navController;
private NewsViewModel newsViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
   //     recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        newsAdapter = new NewsAdapter(ArticlesItem.DiffUtilCallBack, this);
        recyclerView.setAdapter(newsAdapter);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NewsViewModel articleViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        navController = Navigation.findNavController(view);

        EditText searchQuery = getActivity().findViewById(R.id.search_bar);
        // searchQuery.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        String search = searchQuery.getText().toString();
        //  Log.i("Query",search);
        if (!search.isEmpty()) {
            // displaying a progress dialog
            final ProgressDialog progressDialog = new ProgressDialog(getView().getContext());
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setMessage("Loading..");
            Log.i("Query", search + " inside");
            articleViewModel.SearchNews(search, progressDialog).observe(getViewLifecycleOwner(), articlesItems -> newsAdapter.submitList(articlesItems));
        }
    }


    @Override
    public void openArticle(int position) {
        ArticlesItem articles = newsAdapter.getCurrentList().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", articles);
        navController.navigate(R.id.action_searchNewsFragment_to_articleFragment, bundle);
    }


}

