package com.example.droidconsecuritysample.ui.post.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.droidconsecuritysample.R
import com.example.droidconsecuritysample.ui.common.base.BaseActivity

/**
 * @author taiful
 * @since 12/6/25
 */
class PostActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        initView()
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerView)
    }
}