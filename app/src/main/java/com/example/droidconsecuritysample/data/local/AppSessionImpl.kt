package com.example.droidconsecuritysample.data.local

/**
 * @author taiful
 * @since 17/6/25
 */
class AppSessionImpl(private val encryptedPrefsRepository: EncryptedPrefsRepository) : AppSession {

    override var userName: String
        get() = encryptedPrefsRepository.retrieveDecrypted(KEY_USER_NAME)
        set(value) = encryptedPrefsRepository.storeEncrypted(KEY_USER_NAME, value)


    companion object {
        private const val KEY_USER_NAME = "key_user_name"
    }
}