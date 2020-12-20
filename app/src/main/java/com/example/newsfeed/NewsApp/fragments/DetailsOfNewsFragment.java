package com.example.newsfeed.NewsApp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.NewsApp.NewsActivity;
import com.example.newsfeed.NewsApp.UI.NewsViewModel;
import com.example.newsfeed.NewsApp.adapters.NewsAdapter;
import com.example.newsfeed.NewsApp.api.NewsApi;
import com.example.newsfeed.NewsApp.api.RetrofitInstance;
import com.example.newsfeed.NewsApp.models.ArticlesItem;
import com.example.newsfeed.NewsApp.models.NewsResponse;
import com.example.newsfeed.NewsApp.util.Constants;
import com.example.newsfeed.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsOfNewsFragment extends Fragment  implements NewsAdapter.ArticleClickInterface
{
    public DetailsOfNewsFragment()
    {

    }


    String message;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;


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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       recyclerView = getActivity().findViewById(R.id.RecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getView().getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter=new NewsAdapter(ArticlesItem.DiffUtilCallBack,this);
        recyclerView.setAdapter(newsAdapter);
        if (getArguments() != null) {
            DetailsOfNewsFragmentArgs args = DetailsOfNewsFragmentArgs.fromBundle(getArguments());
            message = args.getMessage();
        }
        NewsViewModel articleViewModel=new ViewModelProvider(this).get(NewsViewModel.class);
        articleViewModel.getBreakingNews(message).observe(getViewLifecycleOwner(), new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articlesItems) {
                newsAdapter.submitList(articlesItems);
            }
        });
        return inflater.inflate(R.layout.fragment_details, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void openArticle(int position) {

    }

    @Override
    public void saveArticle(int position) {

    }

}
