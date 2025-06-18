package com.example.droidconsecuritysample.ui.post

import com.example.droidconsecuritysample.data.dto.Post
import com.example.droidconsecuritysample.ui.common.base.MvpPresenter
import com.example.droidconsecuritysample.ui.common.base.MvpView

/**
 * @author taiful
 * @since 14/6/25
 */
interface PostContract {
    interface PostView : MvpView {
        fun onPostSuccess(posts: List<Post>)
    }

    interface PostPresenter : MvpPresenter<PostView> {
        fun getPosts()
    }
}