package com.example.parkingap6800.activities

import android.content.Intent
import com.example.parkingap6800.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ProcessingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing)

        // Set an OnClickListener on the root view to detect clicks anywhere on the screen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            navigateToPaymentSuccess()
        }

        // Automatically navigate to PaymentSuccessActivity after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToPaymentSuccess()
        }, 3000) // 3000 milliseconds = 3 seconds
    }

    private fun navigateToPaymentSuccess() {
        val intent = Intent(this@ProcessingActivity, PaymentSuccessActivity::class.java)
        startActivity(intent)
        finish() // Close ProcessingActivity
    }
}
