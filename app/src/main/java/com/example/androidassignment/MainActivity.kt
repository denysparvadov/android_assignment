package com.example.androidassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var api: Api
    lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setSelectedItemsCount(4)
        adapter = PostsAdapter(mutableListOf())
        recyclerView.adapter = adapter
        api = (application as App).api
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.apiInterface?.getPosts("story", 1)
            response?.let {
                if (response.isSuccessful) {
                    launch(Dispatchers.Main) {
                        adapter.add(response.body()?.hits)
                    }
                }
            }
        }
    }

    private fun setSelectedItemsCount(count: Int) {
        supportActionBar?.title = "$count"
    }

}
