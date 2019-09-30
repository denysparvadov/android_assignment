package com.example.androidassignment

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("hits") val hits: List<Post>,
    @SerializedName("nbPages") val pagesCount: Int
)