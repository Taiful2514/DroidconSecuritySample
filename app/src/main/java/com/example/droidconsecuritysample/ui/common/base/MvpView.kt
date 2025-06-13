package com.example.droidconsecuritysample.ui.common.base

import androidx.annotation.StringRes

/**
 * @author Md. Taiful Islam
 * @since 14/06/25
 */
interface MvpView {
    fun showLoading()
    fun hideLoading()
    fun showMessage(message: String)
    fun showMessage(@StringRes resId: Int)
}