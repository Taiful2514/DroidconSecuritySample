package com.example.droidconsecuritysample.util

/**
 * @author taiful
 * @since 14/6/25
 */
interface ApiCallBack<T> {
    fun onSuccess(t: T)
    fun onError(t: Throwable)
}