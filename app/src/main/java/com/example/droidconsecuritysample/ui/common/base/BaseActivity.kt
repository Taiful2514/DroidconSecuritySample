package com.example.droidconsecuritysample.ui.common.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spanned
import android.view.Gravity
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.droidconsecuritysample.R
import com.example.droidconsecuritysample.util.CommonTasks.showToastMessage

/**
 * @author taiful
 * @since 14/6/25
 */
abstract class BaseActivity : AppCompatActivity(), MvpView {

    private var customProgressDialog: Dialog? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupProgressDialog()
    }

    private fun setupProgressDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_custom_loader)
        val dialogWindowAttributes = dialog.window?.attributes
        dialogWindowAttributes?.gravity = Gravity.BOTTOM
        dialogWindowAttributes?.width = ActionBar.LayoutParams.MATCH_PARENT
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setCancelable(false)
        this.customProgressDialog = dialog
    }

    private fun showSheetLoading(
        title: String = getString(R.string.loading),
        description: String = getString(R.string.please_wait_a_few_moments),
        spannedDescription: Spanned? = null,
    ) {
        val customProgressDialog = customProgressDialog ?: return
        try {
            val progressDialogTitleTv: TextView = customProgressDialog.findViewById(R.id.titleTv)
            val progressDialogDescriptionTv: TextView =
                customProgressDialog.findViewById(R.id.descriptionTv)
            progressDialogTitleTv.text = title
            if (spannedDescription == null) {
                progressDialogDescriptionTv.text = description
            } else {
                progressDialogDescriptionTv.text = spannedDescription
            }
            customProgressDialog.show()
        } catch (ex: Exception) {
            // TODO need to log crash
        }
    }

    override fun showLoading() {
        showSheetLoading()
    }

    override fun hideLoading() {
        try {
            customProgressDialog?.dismiss()
        } catch (ex: IllegalStateException) {
            // TODO need to log crash
        }
    }

    override fun showMessage(message: String) {
        showToastMessage(this, message)
    }

    override fun showMessage(resId: Int) {
        showToastMessage(this, getString(resId))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }
}

