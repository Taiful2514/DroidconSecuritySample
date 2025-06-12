package com.example.droidconsecuritysample.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.droidconsecuritysample.R
import com.example.droidconsecuritysample.util.addSingleClickListener

/**
 * @author taiful
 * @since 12/6/25
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.paidBtn).addSingleClickListener {
            val bottomSheet = KeyInputBottomSheet {
                startActivity(Intent(this, PaidActivity::class.java))
            }
            bottomSheet.show(supportFragmentManager, "KeyInputSheet")
        }
    }
}