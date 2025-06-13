package com.example.droidconsecuritysample.ui.common.di.component

import com.example.droidconsecuritysample.data.network.ApiService
import com.example.droidconsecuritysample.ui.common.di.ApplicationScope
import com.example.droidconsecuritysample.ui.common.di.modules.NetworkModule
import dagger.Component

/**
 * @author taiful
 * @since 14/6/25
 */
@Component(modules = [NetworkModule::class])
@ApplicationScope
interface AppComponent {
    val apiService: ApiService
}