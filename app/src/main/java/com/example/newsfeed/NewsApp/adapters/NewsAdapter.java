package com.example.newsfeed.NewsApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsfeed.R;
import com.example.newsfeed.NewsApp.models.ArticlesItem;

import java.util.List;

public class NewsAdapter extends ListAdapter<ArticlesItem,NewsAdapter.ArticleViewHolder>
{
   // private OnItemClickListener onItemClickListener;
    ArticleClickInterface articleClickInterface;
    public NewsAdapter(@NonNull DiffUtil.ItemCallback<ArticlesItem> diffCallback, ArticleClickInterface articleClickInterface) {
        super(diffCallback);
        this.articleClickInterface=articleClickInterface;
    }
    private static final String TAG = "NewsViewModel.Articles";


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.article_item_display, parent, false);
        return new ArticleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
ArticlesItem articles=getItem(position);
holder.bind(articles);
   //     ArticlesItem articles = (ArticlesItem) differ.getCurrentList().get(position);
    }
    class ArticleViewHolder extends RecyclerView.ViewHolder
    {
        ImageView  ArticleImage;
        ImageButton BookMarksButton;
        TextView tvTitle, tvDescription, tvPublishedAt;
        public ArticleViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ArticleImage = itemView.findViewById(R.id.ivArticleImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPublishedAt = itemView.findViewById(R.id.tvPublishedAt);
            BookMarksButton = itemView.findViewById(R.id.ivBookMarksButton);
            itemView.setOnClickListener(v -> articleClickInterface.openArticle(getAdapterPosition()));
            BookMarksButton.setOnClickListener(v -> articleClickInterface.saveArticle(getAdapterPosition()));
        }
        public void bind(ArticlesItem articles)
        {
            tvTitle.setText(articles.getTitle());
            tvDescription.setText(articles.getDescription());
            tvPublishedAt.setText(articles.getPublishedAt());
            Glide.with(itemView.getContext()).load(articles.getUrlToImage()).
                    centerCrop().into(ArticleImage);
        }
    }
    public interface ArticleClickInterface
    {
     public void openArticle(int position);
     public void saveArticle(int position);
    }
}
