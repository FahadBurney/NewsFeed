package com.example.newsfeed.NewsApp.fragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.NewsApp.NewsActivity;
import com.example.newsfeed.NewsApp.UI.NewsViewModel;
import com.example.newsfeed.NewsApp.adapters.NewsAdapter;
import com.example.newsfeed.NewsApp.models.ArticlesItem;
import com.example.newsfeed.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class BookMarksFragment extends Fragment implements NewsAdapter.ArticleClickInterface {

    private NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;
    private NavController navController;
    private RecyclerView recyclerView;
    ItemTouchHelper.SimpleCallback simpleCallback = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        return view;

    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        newsAdapter = new NewsAdapter(ArticlesItem.DiffUtilCallBack, this);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
    }

    public BookMarksFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        setupRecyclerView(view);
       // ArticlesItem articlesItem=null;
        // now we will call the liveData method that shows our articles that are saved in database
        // inside our recycler view
        newsViewModel.getSavedNews().observe(getViewLifecycleOwner(), new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articlesItems) {
                newsAdapter.submitList(articlesItems);
            }
        });
        navController = Navigation.findNavController(view);
        simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    int position = viewHolder.getAdapterPosition();
                    ArticlesItem articlesItem = newsAdapter.getCurrentList().get(position);
                    newsViewModel.deleteArticle(articlesItem);

                    Snackbar.make(view, "Successfully Deleted Article", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newsViewModel.saveArticle(articlesItem);
                        }
                    }).show();
                    // articlesItem=newsAdapter.getCurrentList().remove(position);
                    // newsAdapter.submitList();
                }
            }
/*
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {


                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(),R.color.red))
                        .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.teal_200))
                        .addActionIcon(R.drawable.ic_delete_icon)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } */
        };
    ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
    itemTouchHelper.attachToRecyclerView(recyclerView);
    }






    @Override
    public void openArticle(int position) {
        //here we get the article that we want to pass to Article Fragment
        ArticlesItem articles = newsAdapter.getCurrentList().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", articles);
        navController.navigate(R.id.action_bookMarksFragment_to_articleFragment, bundle);
    }

}

