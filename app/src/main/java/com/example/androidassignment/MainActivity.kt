package com.example.androidassignment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var api: Api
    lateinit var adapter: PostsAdapter
    private var currentPage: Int = 1
    private var totalPages: Int = 0
    private var pastVisiblesItems: Int? = 0
    private var visibleItemCount: Int? = 0
    private var totalItemCount: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setSelectedItemsCount(4)
        adapter = PostsAdapter(mutableListOf(), ::postWasChecked)
        recyclerView.adapter = adapter
        api = (application as App).api
        initPaginate()
        refreshLayout.setOnRefreshListener {
            getPosts(1)
        }
        getPosts(currentPage)
    }

    private fun postWasChecked() {
        setSelectedItemsCount(adapter.list.filter { it.isSelected }.size)
    }

    private fun initPaginate() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = recyclerView.layoutManager?.childCount
                    totalItemCount = recyclerView.layoutManager?.itemCount
                    pastVisiblesItems =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                    if (!refreshLayout.isRefreshing && currentPage <= totalPages) {
                        if ((visibleItemCount!!.plus(pastVisiblesItems!!)) >= totalItemCount?.minus(
                                visibleItemCount!! * 3
                            )!!
                        ) {
                            getPosts(++currentPage)
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            }
        })
    }

    private fun getPosts(page: Int) {
        currentPage = page
        setSelectedItemsCount(0)
        refresh(true)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.apiInterface?.getPosts("story", currentPage)
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            totalPages = it.pagesCount
                            launch(Dispatchers.Main) {
                                adapter.add(it.hits, currentPage)
                                refresh(false)
                            }
                        }
                    }

                }
            } catch (e: Exception) {
                currentPage--
                launch(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, R.string.load_error, Toast.LENGTH_LONG).show()
                }
                refresh(false)
            }
        }
    }


    private fun setSelectedItemsCount(count: Int) {
        supportActionBar?.title = "Posts were checked $count"
    }

    private fun refresh(boolean: Boolean) {
        refreshLayout.isRefreshing = boolean
    }
}
