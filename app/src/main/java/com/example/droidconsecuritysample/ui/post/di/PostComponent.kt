package com.example.droidconsecuritysample.ui.post.di

import com.example.droidconsecuritysample.common.di.PerActivity
import com.example.droidconsecuritysample.common.di.component.AppComponent
import com.example.droidconsecuritysample.ui.post.PostContract
import dagger.Component

/**
 * @author taiful
 * @since 14/6/25
 */
@Component(modules = [PostModule::class], dependencies = [AppComponent::class])
@PerActivity
interface PostComponent {
    val presenter: PostContract.PostPresenter
}