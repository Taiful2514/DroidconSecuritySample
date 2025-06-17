package com.example.droidconsecuritysample.common.di.component

import com.example.droidconsecuritysample.data.network.ApiHelper
import com.example.droidconsecuritysample.common.di.ApplicationScope
import com.example.droidconsecuritysample.common.di.modules.LocalDataModule
import com.example.droidconsecuritysample.common.di.modules.NetworkModule
import com.example.droidconsecuritysample.data.local.AppSession
import dagger.Component

/**
 * @author taiful
 * @since 14/6/25
 */
@Component(modules = [NetworkModule::class, LocalDataModule::class])
@ApplicationScope
interface AppComponent {
    val apiHelper: ApiHelper
    val appSession: AppSession
}