package com.example.droidconsecuritysample.data.local

import android.content.SharedPreferences
import com.example.droidconsecuritysample.util.CryptoUtils
import androidx.core.content.edit
import com.example.droidconsecuritysample.util.Constant.EMPTY_STRING

/**
 * @author taiful
 * @since 17/6/25
 */
class EncryptedPrefsRepository(
    private val sharedPreference: SharedPreferences
) {

    fun storeEncrypted(key: String, value: String) {
        val data = CryptoUtils.encrypt(value)
        sharedPreference.edit { putString(key, data) }
    }

    fun retrieveDecrypted(key: String): String {
        val encrypted = sharedPreference.getString(key, null) ?: return EMPTY_STRING
        return CryptoUtils.decrypt(encrypted)
    }
}