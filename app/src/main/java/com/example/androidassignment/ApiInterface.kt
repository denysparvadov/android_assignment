package com.example.androidassignment

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(Constants.GET_POSTS)
    suspend fun getPosts(@Query(value = "tags") tags: String, @Query("page") page: Int): Response<Search>
}