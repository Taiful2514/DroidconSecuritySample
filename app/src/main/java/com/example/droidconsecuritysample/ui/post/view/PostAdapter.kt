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
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvBody: TextView = itemView.findViewById(R.id.tvBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.tvTitle.text = post.title
        holder.tvBody.text = post.body
    }

    override fun getItemCount(): Int = posts.size
}
