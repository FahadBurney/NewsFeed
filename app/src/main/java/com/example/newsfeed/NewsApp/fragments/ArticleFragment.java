package com.example.newsfeed.NewsApp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgs;

import com.example.newsfeed.NewsApp.NewsActivity;
import com.example.newsfeed.NewsApp.UI.NewsViewModel;
import com.example.newsfeed.NewsApp.models.ArticlesItem;
import com.example.newsfeed.R;

import java.util.Properties;
import java.util.PropertyResourceBundle;

public class ArticleFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }
    private NewsViewModel newsViewModel;
    private WebView webView;
public static final String TAG="About Articles";
    ArticleFragmentArgs args=ArticleFragmentArgs.fromBundle(getArguments());
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsViewModel=((NewsActivity)getActivity()).ViewModel;
     //   Bundle bundle=getArguments();
        ArticlesItem articles=args.getArticle();


        Log.i("ArticlesFromParcelable",articles.toString());
   //     PropertyResourceBundle propertyResourceBundle=args.g
        Log.i(TAG, "articles->in fragment"+articles.getUrl()+"\n"+articles.getDescription());
        //displaying them in webView
webView=getView().findViewById(R.id.webView);
webView.setWebViewClient(new WebViewClient());
webView.loadUrl(articles.getUrl());
    }
}
