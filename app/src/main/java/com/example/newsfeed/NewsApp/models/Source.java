package com.example.newsfeed.NewsApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source{
	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("name")
	@Expose
	private String name;

	public Source(String name) {
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}
}
