package com.example.droidconsecuritysample.common.di.modules

import android.content.Context
import com.example.droidconsecuritysample.common.di.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * @author Md. Taiful Islam
 * @since Jun 17, 2021
 */
@Module
internal class ContextModule(private val context: Context) {

    @Provides
    @ApplicationScope
    fun context(): Context {
        return context
    }
}