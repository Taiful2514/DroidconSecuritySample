package com.example.droidconsecuritysample.util

import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import java.util.concurrent.TimeUnit

/**
 * @author taiful
 * @since 12/6/25
 */

/**
 * Adds a click listener to the view that ignores repeated clicks within 1 second.
 * Useful for preventing accidental double taps or multiple rapid clicks.
 */
fun View.addSingleClickListener(action: () -> Unit) {
    this.clicks()
        .throttleFirst(1, TimeUnit.SECONDS)
        .subscribe { action.invoke() }
}