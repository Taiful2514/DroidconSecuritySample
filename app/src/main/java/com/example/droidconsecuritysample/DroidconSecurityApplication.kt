package com.example.droidconsecuritysample

import android.app.Application
import com.example.droidconsecuritysample.common.di.component.AppComponent
import com.example.droidconsecuritysample.common.di.component.DaggerAppComponent
import com.example.droidconsecuritysample.common.di.modules.ContextModule
import io.reactivex.rxjava3.plugins.RxJavaPlugins

/**
 * @author taiful
 * @since 14/6/25
 */
class DroidconSecurityApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        RxJavaPlugins.setErrorHandler {
            // Do nothing
        }
    }

    companion object {
        @get:Synchronized
        var instance: DroidconSecurityApplication? = null
            private set
    }
}