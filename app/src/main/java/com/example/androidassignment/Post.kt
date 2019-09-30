package com.example.androidassignment

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("title") val title: String,
    @SerializedName("created_at") val createdAt: String,
    var isSelected: Boolean = false
)