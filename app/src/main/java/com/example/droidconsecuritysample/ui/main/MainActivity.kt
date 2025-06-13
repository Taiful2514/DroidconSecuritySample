package com.example.droidconsecuritysample.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.droidconsecuritysample.R
import com.example.droidconsecuritysample.ui.paid.PaidActivity
import com.example.droidconsecuritysample.ui.post.view.PostActivity
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

        findViewById<Button>(R.id.postListBtn).addSingleClickListener {
            startActivity(Intent(this, PostActivity::class.java))
        }
    }
}