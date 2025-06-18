package com.example.droidconsecuritysample.ui.common.base

/**
 * @author Md. Taiful Islam
 * @since 14/06/25
 */
interface MvpPresenter<V : MvpView> {
    fun onAttach(mvpView: V)
    fun onDetach()
}