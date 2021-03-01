package com.example.newsfeed.NewsApp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfeed.NewsApp.models.ArticlesItem
import com.example.newsfeed.R
import kotlinx.android.synthetic.main.article_item_display.view.*
import java.text.SimpleDateFormat


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    //creating inner class first
    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // creating a diffCallBack as it is important for using with async List Differ
    private val differCallBack = object : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }

    // creating async list differ now
    val asyncDiffer = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_item_display, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articlesItem = asyncDiffer.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(articlesItem?.urlToImage).centerCrop().into(ivArticleImage)
            tvTitle.text = articlesItem?.title
            tvDescription.text = articlesItem?.description
            try {
                val s1: String? = articlesItem?.publishedAt
                val d = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(s1)
                var publishedAt: String? = ""
                if (d != null) {
                    publishedAt = SimpleDateFormat("MMMM dd, yyyy ").format(d)
                }
                tvPublishedAt.text = publishedAt
            } catch (e: Exception) {
                e.printStackTrace()
            }
           // tvPublishedAt.text = articlesItem?.publishedAt
            readMore.setOnClickListener {
                onItemClickListener?.let {
                   it(articlesItem)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
    }
}

private var onItemClickListener: ((ArticlesItem) -> Unit)? = null

fun setOnItemClickListener(listener: (ArticlesItem) -> Unit) {
    Log.d("THEListener", "articlesUrl is $listener")
    onItemClickListener = listener
}