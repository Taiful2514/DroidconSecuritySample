package com.example.droidconsecuritysample.ui.post.view

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.droidconsecuritysample.R
import com.example.droidconsecuritysample.data.dto.Post
import com.example.droidconsecuritysample.ui.common.base.BaseActivity
import com.example.droidconsecuritysample.ui.post.PostContract
import com.example.droidconsecuritysample.ui.post.di.DaggerPostComponent
import com.example.droidconsecuritysample.util.CommonTasks

/**
 * @author taiful
 * @since 12/6/25
 */
class PostActivity : BaseActivity(), PostContract.PostView {

    private lateinit var mPresenter: PostContract.PostPresenter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        init()
        initView()
        mPresenter.getPosts()
    }

    private fun init() {
        mPresenter = DaggerPostComponent.builder()
            .appComponent(CommonTasks.appComponent)
            .build()
            .presenter
        mPresenter.onAttach(this)
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerView)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Posts"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun onPostSuccess(posts: List<Post>) {
        recyclerView.adapter = PostAdapter(posts)
    }
}