package com.example.droidconsecuritysample.ui.post.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.droidconsecuritysample.R
import com.example.droidconsecuritysample.data.dto.Post

/**
 * @author taiful
 * @since 12/6/25
 */
class PostAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindValues(posts[position])
    }

    override fun getItemCount(): Int = posts.size
}
