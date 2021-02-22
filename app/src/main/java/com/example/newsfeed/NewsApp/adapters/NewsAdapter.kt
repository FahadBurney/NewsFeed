package com.example.newsfeed.NewsApp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfeed.NewsApp.adapters.NewsAdapter.ArticleViewHolder
import com.example.newsfeed.NewsApp.models.ArticlesItem
import com.example.newsfeed.R
import java.text.SimpleDateFormat

class NewsAdapter(diffCallback: DiffUtil.ItemCallback<ArticlesItem?>, //  private ArticlesItem OnItemClickListener;
        // private OnItemClickListener onItemClickListener;
                  var articleClickInterface: ArticleClickInterface) : ListAdapter<ArticlesItem?, ArticleViewHolder>(diffCallback) {
    /*public  void setOnItemClickListener(ArticlesItem articlesItem)
    {
        OnItemClickListener=articlesItem;
    }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.article_item_display, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articles = currentList[position]!!
        holder.bind(articles)
        // ArticlesItem articles = (ArticlesItem) differ.getCurrentList().get(position);
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ArticleImage: ImageView
        var tvTitle: TextView
        var tvDescription: TextView
        var tvPublishedAt: TextView
        var tvReadMore: TextView
        fun bind(articles: ArticlesItem) {
            tvTitle.text = articles.title
            tvDescription.text = articles.description
            try {
                val s1 = articles.publishedAt
                val d = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(s1)
                var publishedAt: String? = ""
                if (d != null) {
                    publishedAt = SimpleDateFormat("MMMM dd, yyyy ").format(d)
                }
                tvPublishedAt.text = publishedAt
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Glide.with(itemView.context).load(articles.urlToImage).centerCrop().into(ArticleImage)
        }

        init {
            ArticleImage = itemView.findViewById(R.id.ivArticleImage)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvPublishedAt = itemView.findViewById(R.id.tvPublishedAt)
            tvReadMore = itemView.findViewById(R.id.readMore)
            tvReadMore.setOnClickListener { v: View? ->
                articleClickInterface.openArticle(adapterPosition)
                Log.i(TAG, "getAdapterPosition() $adapterPosition")
            }
        }
    }

    interface ArticleClickInterface {
        fun openArticle(position: Int) //  void saveArticle(int position);
    }

    companion object {
        const val TAG = "POSITION"
    }
}