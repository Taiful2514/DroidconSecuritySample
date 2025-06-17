package com.example.droidconsecuritysample.util

object SecurityUtils {
    init {
        System.loadLibrary("native-lib")
    }

    external fun getBaseUrl(): String
    external fun getPaidKey():String
    external fun getSSLPin():String
    external fun pinningUrl():String
}
