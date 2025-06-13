package com.example.droidconsecuritysample.data.network

import com.example.droidconsecuritysample.data.dto.Post
import com.example.droidconsecuritysample.util.ApiCallBack
import io.reactivex.rxjava3.annotations.CheckReturnValue
import io.reactivex.rxjava3.disposables.Disposable

/**
 * @author taiful
 * @since 14/6/25
 */
interface ApiHelper {

    @CheckReturnValue
    fun getPosts(apiCallBack: ApiCallBack<List<Post>>): Disposable
}