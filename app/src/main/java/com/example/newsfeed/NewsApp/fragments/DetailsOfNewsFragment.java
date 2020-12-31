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
import androidx.lifecycle.ViewModelStoreOwner;
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

public class DetailsOfNewsFragment extends Fragment implements NewsAdapter.ArticleClickInterface {
    public DetailsOfNewsFragment() {

    }

    String message;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
public static final String TAG="DetailsOfNewsFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


//fragmentTransaction.remove(y).commit()
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
    Log.i(TAG,"onCreateView() is created in DetailsOfNewsFragment");
        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        newsAdapter = new NewsAdapter(ArticlesItem.DiffUtilCallBack, this);
        recyclerView.setAdapter(newsAdapter);
        if (getArguments() != null) {
            DetailsOfNewsFragmentArgs args = DetailsOfNewsFragmentArgs.fromBundle(getArguments());
            message = args.getMessage();
        }
        Log.i("TAG",message);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NewsViewModel articleViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        articleViewModel.getBreakingNews(message).observe(requireActivity(), new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articlesItems) {
                newsAdapter.submitList(articlesItems);
            }
        });
      //  Log.i(TAG,"onViewCreated() is created in DetailsOfNewsFragment");
        Log.i("TAG",message+" onViewCreated()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void openArticle(int position) {

    }

    @Override
    public void saveArticle(int position) {

    }

}
