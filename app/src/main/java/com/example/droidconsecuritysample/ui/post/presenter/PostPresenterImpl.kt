package com.example.droidconsecuritysample.ui.post.presenter

import com.example.droidconsecuritysample.data.dto.Post
import com.example.droidconsecuritysample.data.network.ApiHelper
import com.example.droidconsecuritysample.ui.common.base.BaseMvpPresenter
import com.example.droidconsecuritysample.ui.post.PostContract
import com.example.droidconsecuritysample.util.ApiCallBack
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * @author taiful
 * @since 14/6/25
 */
class PostPresenterImpl(
    compositeDisposable: CompositeDisposable,
    private val apiHelper: ApiHelper
) : BaseMvpPresenter<PostContract.PostView>(compositeDisposable), PostContract.PostPresenter {

    override fun getPosts() {
        view?.showLoading()
        val disposable = apiHelper.getPosts(object : ApiCallBack<List<Post>> {
            override fun onSuccess(posts: List<Post>) {
                safeExecute { view ->
                    view.hideLoading()
                    view.onPostSuccess(posts)
                }
            }

            override fun onError(t: Throwable) {
                t.printStackTrace()
                safeExecute { view ->
                    view.hideLoading()
                    view.showMessage("Something went wrong")
                }
            }
        })
        compositeDisposable.add(disposable)
    }
}