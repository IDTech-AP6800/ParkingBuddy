package com.example.parkingap6800.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R

class PaymentSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

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
