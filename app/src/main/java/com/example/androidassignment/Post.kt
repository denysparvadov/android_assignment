package com.example.androidassignment

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("title") val title: String,
    @SerializedName("created_at_i") val createdAt: Long,
    var isSelected: Boolean = false
)