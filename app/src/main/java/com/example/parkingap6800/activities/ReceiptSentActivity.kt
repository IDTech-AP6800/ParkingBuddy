package com.example.parkingap6800.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.parkingap6800.R

class ReceiptSentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt_sent)

        // Navigate to MainActivity after 30 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToMainActivity()
        }, 30000) // 30000 milliseconds = 30 seconds
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close PaymentSuccessActivity
    }
}

