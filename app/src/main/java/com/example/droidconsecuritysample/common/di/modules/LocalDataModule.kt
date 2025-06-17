package com.example.droidconsecuritysample.common.di.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.droidconsecuritysample.common.di.ApplicationScope
import com.example.droidconsecuritysample.data.local.AppSession
import com.example.droidconsecuritysample.data.local.AppSessionImpl
import com.example.droidconsecuritysample.data.local.EncryptedPrefsRepository
import dagger.Module
import dagger.Provides

@Module(includes = [ContextModule::class])
class LocalDataModule {

    @Provides
    @ApplicationScope
    fun appSession(encryptedPrefsRepository: EncryptedPrefsRepository): AppSession {
        return AppSessionImpl(encryptedPrefsRepository)
    }

    @Provides
    @ApplicationScope
    fun encryptedPrefsRepository(sharedPreferences: SharedPreferences): EncryptedPrefsRepository {
        return EncryptedPrefsRepository(sharedPreferences)
    }

    @Provides
    @ApplicationScope
    fun sharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("xyzAbc-123", MODE_PRIVATE)
    }
}
