package com.example.newsfeed.NewsApp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsfeed.NewsApp.models.ArticlesItem;
import com.example.newsfeed.R;

import java.text.SimpleDateFormat;
import java.util.Date;
public class NewsAdapter extends ListAdapter<ArticlesItem, NewsAdapter.ArticleViewHolder> {
    //  private ArticlesItem OnItemClickListener;
    // private OnItemClickListener onItemClickListener;
    ArticleClickInterface articleClickInterface;
    public static final String TAG = "POSITION";
    public NewsAdapter(@NonNull DiffUtil.ItemCallback<ArticlesItem> diffCallback, ArticleClickInterface articleClickInterface) {
        super(diffCallback);
        this.articleClickInterface = articleClickInterface;
    }
    /*public  void setOnItemClickListener(ArticlesItem articlesItem)
    {
        OnItemClickListener=articlesItem;
    }*/
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.article_item_display, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        ArticlesItem articles = getCurrentList().get(position);
        holder.bind(articles);
        // ArticlesItem articles = (ArticlesItem) differ.getCurrentList().get(position);

    }



    class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView ArticleImage;
        TextView tvTitle, tvDescription, tvPublishedAt,tvReadMore;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            ArticleImage = itemView.findViewById(R.id.ivArticleImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPublishedAt = itemView.findViewById(R.id.tvPublishedAt);
            tvReadMore=itemView.findViewById(R.id.readMore);
            tvReadMore.setOnClickListener((View v) -> {
                articleClickInterface.openArticle(getAdapterPosition());
                Log.i(TAG, "getAdapterPosition() " + getAdapterPosition());
            });


        }

        public void bind(ArticlesItem articles) {

            tvTitle.setText(articles.getTitle());
            tvDescription.setText(articles.getDescription());
            try {
                String s1 = articles.getPublishedAt();
                Date d = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")).parse(s1);
                String publishedAt="";
                if(d!=null) {
                    publishedAt = (new SimpleDateFormat("MMMM dd, yyyy ")).format(d);
                }
                tvPublishedAt.setText(publishedAt);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Glide.with(itemView.getContext()).load(articles.getUrlToImage()).
                    centerCrop().into(ArticleImage);

        }
    }

    public interface ArticleClickInterface {
        void openArticle(int position);

      //  void saveArticle(int position);
    }
}