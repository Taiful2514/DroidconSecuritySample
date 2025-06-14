package com.example.droidconsecuritysample.ui.post.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.droidconsecuritysample.R
import com.example.droidconsecuritysample.data.dto.Post

/**
 * @author taiful
 * @since 14/6/25
 */
class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    private val tvBody: TextView = itemView.findViewById(R.id.tvBody)

    fun bindValues(post: Post) {
        tvTitle.text = post.title
        tvBody.text = post.body
    }
}