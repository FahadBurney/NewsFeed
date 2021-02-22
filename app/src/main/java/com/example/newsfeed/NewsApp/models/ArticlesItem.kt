package com.example.newsfeed.NewsApp.models

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "articles", indices = [Index(value = ["url"], unique = true)]) //(onConflict = OnConflictStrategy.REPLACE)
class ArticlesItem : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @SerializedName("source")
    @Expose
    var source: Source? = null

    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String? = null

    /*
private boolean present=false;

   public boolean isPresent() {
       return present;
   }

   public void setPresent(boolean present) {
       this.present = present;
   }
*/
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ArticlesItem
        return id == that.id &&
                source == that.source &&
                author == that.author &&
                title == that.title &&
                description == that.description &&
                url == that.url &&
                urlToImage == that.urlToImage &&
                publishedAt == that.publishedAt &&
                content == that.content
    }

    override fun hashCode(): Int {
        return Objects.hash(id, source, author, title, description, url, urlToImage, publishedAt, content)
    }

    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null
    override fun toString(): String {
        return "ArticlesItem{" +
                "id=" + id +
                ", source=" + source +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", content='" + content + '\'' +
                '}'
    }

    companion object {
        @JvmField
        val DiffUtilCallBack: DiffUtil.ItemCallback<ArticlesItem> = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem,
                                         newItem: ArticlesItem): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: ArticlesItem,
                                            newItem: ArticlesItem): Boolean {
                return oldItem.content == newItem.content
            }
        }
    }
}