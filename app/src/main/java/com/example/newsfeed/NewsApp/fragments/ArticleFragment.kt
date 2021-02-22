package com.example.newsfeed.NewsApp.fragments;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavArgs;

import com.example.newsfeed.NewsApp.NewsActivity;
import com.example.newsfeed.NewsApp.UI.NewsViewModel;
import com.example.newsfeed.NewsApp.models.ArticlesItem;
import com.example.newsfeed.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.util.Properties;
import java.util.PropertyResourceBundle;

public class ArticleFragment extends Fragment
{
    public ArticleFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }
    public static final String TAG="About Articles";
    private NewsViewModel newsViewModel;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArticleFragmentArgs args=ArticleFragmentArgs.fromBundle(getArguments());

        //displaying them in webView
        WebView webView = getView().findViewById(R.id.webView);
webView.setWebViewClient(new WebViewClient());
webView.loadUrl(args.getArticle().getUrl());

        FloatingActionButton fab=getView().findViewById(R.id.BookMarksButton);
        newsViewModel=new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsViewModel.saveArticle(args.getArticle());
                Toast.makeText(getContext(),"BookMarked",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
