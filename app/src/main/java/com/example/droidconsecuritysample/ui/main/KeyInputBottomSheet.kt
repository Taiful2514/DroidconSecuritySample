package com.example.droidconsecuritysample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.droidconsecuritysample.R
import com.example.droidconsecuritysample.util.Constant.PAID_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

/**
 * @author taiful
 * @since 12/6/25
 */
class KeyInputBottomSheet(
    private val onKeyEnteredSuccess: () -> Unit
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.bottom_sheet_key_input, container, false)

        val inputField = view.findViewById<EditText>(R.id.editKey)
        val submitButton = view.findViewById<Button>(R.id.btnSubmit)
        val inputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayout)

        inputField.addTextChangedListener {
            inputLayout.error = null
        }

        submitButton.setOnClickListener {
            val key = inputField.text.toString().trim()
            if (key.isEmpty()) {
                inputLayout.error = "Key cannot be empty"
            } else if (key != PAID_KEY) {
                inputLayout.error = "Invalid key"
            } else {
                inputLayout.error = null // Clear previous errors
                onKeyEnteredSuccess()
                dismiss()
            }
        }

        return view
    }
}
