package com.example.droidconsecuritysample.data.network

import com.example.droidconsecuritysample.data.dto.Post
import com.example.droidconsecuritysample.util.ApiCallBack
import com.example.droidconsecuritysample.util.withSubscriberOnWorkerThread
import io.reactivex.rxjava3.disposables.Disposable

/**
 * @author taiful
 * @since 14/6/25
 */
class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override fun getPosts(apiCallBack: ApiCallBack<List<Post>>): Disposable {
        return apiService.getPosts().withSubscriberOnWorkerThread(apiCallBack)
    }
}