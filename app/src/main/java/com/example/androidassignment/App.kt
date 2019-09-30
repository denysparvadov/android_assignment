package com.example.androidassignment

import android.app.Application

class App : Application() {
    lateinit var api: Api
    override fun onCreate() {
        super.onCreate()
        api = Api()
    }
}