package com.example.droidconsecuritysample.data.network

import com.example.droidconsecuritysample.data.dto.Post
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

/**
 * @author taiful
 * @since 14/6/25
 */
interface ApiService {

    @GET("posts")
    fun getPosts(): Observable<List<Post>>
}