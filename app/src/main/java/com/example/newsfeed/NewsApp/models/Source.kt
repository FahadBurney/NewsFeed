package com.example.newsfeed.NewsApp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Source(@field:Expose @field:SerializedName("name") val name: String) {
    @SerializedName("id")
    @Expose
    val id: String? = null

}