package com.example.androidassignment

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api {
    var apiInterface: ApiInterface? = null

    private val initOkkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        val gson = GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(initOkkHttpClient)
            .build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }
}