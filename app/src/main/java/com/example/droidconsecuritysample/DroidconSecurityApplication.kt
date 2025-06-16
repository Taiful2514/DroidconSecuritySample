package com.example.droidconsecuritysample

import android.app.Application
import com.example.droidconsecuritysample.ui.common.di.component.AppComponent
import com.example.droidconsecuritysample.ui.common.di.component.DaggerAppComponent
import io.reactivex.rxjava3.plugins.RxJavaPlugins

/**
 * @author taiful
 * @since 14/6/25
 */
class DroidconSecurityApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
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