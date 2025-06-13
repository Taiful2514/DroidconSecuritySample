package com.example.droidconsecuritysample.util

import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author taiful
 * @since 12/6/25
 */

/**
 * Adds a click listener to the view that ignores repeated clicks within 1 second.
 * Useful for preventing accidental double taps or multiple rapid clicks.
 */
fun View.addSingleClickListener(action: () -> Unit) {
    this.clicks()
        .throttleFirst(1, TimeUnit.SECONDS)
        .subscribe { action.invoke() }
}

fun <T : Any> Observable<T>.withScheduler(): Observable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}


fun <T : Any> Observable<T>.withSubscriberOnWorkerThread(apiCallBack: ApiCallBack<T>): Disposable {
    return withScheduler()
        .subscribe({ data: T -> apiCallBack.onSuccess(data) },
            { throwable: Throwable -> apiCallBack.onError(throwable) })
}