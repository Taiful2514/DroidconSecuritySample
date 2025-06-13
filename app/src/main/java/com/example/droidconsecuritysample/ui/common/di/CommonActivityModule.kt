package com.example.droidconsecuritysample.ui.common.di

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * @author Md. Taiful Islam
 * @since 14/06/25
 */
@Module
internal class CommonActivityModule {
    @Provides
    @PerActivity
    fun compositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}