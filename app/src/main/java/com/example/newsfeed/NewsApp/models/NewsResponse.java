package com.example.newsfeed.NewsApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse{

	@SerializedName("articles")
	@Expose
	private List<ArticlesItem> articles;
	@SerializedName("totalResults")
	@Expose
	private int totalResults;
	@SerializedName("status")
	@Expose
	private String status;

	public int getTotalResults(){
		return totalResults;
	}

	public List<ArticlesItem> getArticles(){
		return articles;
	}

	public String getStatus(){
		return status;
	}
}