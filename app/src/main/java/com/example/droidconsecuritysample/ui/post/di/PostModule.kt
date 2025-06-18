package com.example.droidconsecuritysample.ui.post.di

import com.example.droidconsecuritysample.data.network.ApiHelper
import com.example.droidconsecuritysample.common.di.CommonActivityModule
import com.example.droidconsecuritysample.common.di.PerActivity
import com.example.droidconsecuritysample.ui.post.PostContract
import com.example.droidconsecuritysample.ui.post.presenter.PostPresenterImpl
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * @author taiful
 * @since 14/6/25
 */
@Module(includes = [CommonActivityModule::class])
class PostModule {

    @Provides
    @PerActivity
    fun presenter(
        compositeDisposable: CompositeDisposable,
        apiHelper: ApiHelper
    ): PostContract.PostPresenter {
        return PostPresenterImpl(compositeDisposable, apiHelper)
    }
}