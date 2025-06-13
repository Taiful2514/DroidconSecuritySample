package com.example.droidconsecuritysample.util

import android.content.Context
import android.widget.Toast
import com.example.droidconsecuritysample.util.Constant.EMPTY_STRING

/**
 * @author taiful
 * @since 14/6/25
 */
object CommonTasks {

    fun showToastMessage(context: Context?, message: String?) {
        if (context == null || message == null || message == EMPTY_STRING) {
            return
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}