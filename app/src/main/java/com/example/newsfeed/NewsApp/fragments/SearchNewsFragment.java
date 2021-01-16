package com.example.newsfeed.NewsApp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.NewsApp.NewsActivity;
import com.example.newsfeed.NewsApp.UI.NewsViewModel;
import com.example.newsfeed.NewsApp.adapters.NewsAdapter;
import com.example.newsfeed.NewsApp.models.ArticlesItem;
import com.example.newsfeed.R;

import java.util.List;

import static android.app.ProgressDialog.STYLE_HORIZONTAL;

public class SearchNewsFragment extends Fragment implements NewsAdapter.ArticleClickInterface
{
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        newsAdapter=new NewsAdapter(ArticlesItem.DiffUtilCallBack,this);
        recyclerView.setAdapter(newsAdapter);

        return view;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NewsViewModel articleViewModel=new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        EditText searchQuery= getActivity().findViewById(R.id.search_bar);
        String search=searchQuery.getText().toString();
        Log.i("Query",search+" outside");
        if(!search.isEmpty())
        {
            // displaying a progress dialog
            final ProgressDialog progressDialog=new ProgressDialog(getView().getContext());
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setMessage("Loading..");
            Log.i("Query",search+" inside");
            articleViewModel.SearchNews(search,progressDialog).observe(getViewLifecycleOwner(), new Observer<List<ArticlesItem>>() {
                @Override
                public void onChanged(List<ArticlesItem> articlesItems) {
                    newsAdapter.submitList(articlesItems);
                }
            });
        }
    }

    @Override
    public void openArticle(int position) {

    }

    @Override
    public void saveArticle(int position) {

    }
}
