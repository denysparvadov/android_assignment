package com.example.androidassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import java.text.SimpleDateFormat
import java.util.*

class PostsAdapter(var list: MutableList<Post>, var postWasChecked: () -> Unit) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
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

    fun add(hits: List<Post>?, page: Int) {
        hits?.let {
            if (page == 1) list.clear()
            list.addAll(hits)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(post: Post) {
            view.run {
                title.text = post.title
                val parsedDate = Date(post.createdAt * 1000L)
                date.text = SimpleDateFormat("dd MMMM yyyy 'at' hh:mm").format(parsedDate)
                switcher.setOnCheckedChangeListener { _, isChecked ->
                    post.isSelected = isChecked
                    postWasChecked()
                }
                switcher.isChecked = post.isSelected
                setOnClickListener {
                    switcher.isChecked = !post.isSelected
                }
            }
        }
    }
}