package com.example.androidassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter(var list: MutableList<Post>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_post,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun add(hits: List<Post>?) {
        hits?.let {
            list.addAll(hits)
            notifyDataSetChanged()
        }
    }


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(post: Post) {
            view.title.text = post.title
            view.date.text = post.createdAt
        }
    }
}