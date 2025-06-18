package com.example.droidconsecuritysample.ui.common.base

import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * @author taiful
 * @since 14/6/25
 */
open class BaseMvpPresenter<V : MvpView>(protected val compositeDisposable: CompositeDisposable) :
    MvpPresenter<V> {

    protected var view: V? = null

    override fun onAttach(mvpView: V) {
        view = mvpView
    }

    override fun onDetach() {
        compositeDisposable.dispose()
        view = null
    }

    fun safeExecute(block: (view: V) -> Unit) {
        view?.let(block)
    }
}