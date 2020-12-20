package com.example.newsfeed.NewsApp.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "articles")
public class ArticlesItem
{
	@PrimaryKey(autoGenerate = true)
	private int id;

	@SerializedName("source")
	@Expose
	private Source source;

	@SerializedName("author")
	@Expose
	private String author;

	@SerializedName("title")
	@Expose
	private String title;

	@SerializedName("description")
	@Expose
	private String description;

	@SerializedName("url")
	@Expose
	private String url;

	@SerializedName("urlToImage")
	@Expose
	private String urlToImage;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ArticlesItem that = (ArticlesItem) o;
		return id == that.id &&
				Objects.equals(source, that.source) &&
				Objects.equals(author, that.author) &&
				Objects.equals(title, that.title) &&
				Objects.equals(description, that.description) &&
				Objects.equals(url, that.url) &&
				Objects.equals(urlToImage, that.urlToImage) &&
				Objects.equals(publishedAt, that.publishedAt) &&
				Objects.equals(content, that.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, source, author, title, description, url, urlToImage, publishedAt, content);
	}

	@SerializedName("publishedAt")
	@Expose
	private String publishedAt;

	@SerializedName("content")
	@Expose
	private String content;

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
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
				'}';
	}

	public int getId() {
		return id;
	}
	public String getPublishedAt(){
		return publishedAt;
	}

	public String getAuthor(){
		return author;
	}

	public String getUrlToImage(){
		return urlToImage;
	}

	public String getDescription(){
		return description;
	}

	public Source getSource(){
		return source;
	}

	public String getTitle(){
		return title;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl(){
		return url;
	}

	public String getContent(){
		return content;
	}

	public static final DiffUtil.ItemCallback<ArticlesItem>
			DiffUtilCallBack = new DiffUtil.ItemCallback<ArticlesItem>() {
		@Override
		public boolean areItemsTheSame(@NonNull ArticlesItem oldItem,
									   @NonNull ArticlesItem newItem) {
			return oldItem.getUrl().equals(newItem.getUrl());
		}
		@Override
		public boolean areContentsTheSame(@NonNull ArticlesItem oldItem,
										  @NonNull ArticlesItem newItem) {
			return oldItem.getContent().equals(newItem.getContent());
		}
	};
}
