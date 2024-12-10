package com.example.parkingap6800.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.parkingap6800.R

class ReceiptSentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt_sent)

        // Apply scale animation the green glow
        val scaleAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_up_down)
        val ellipseGlow = findViewById<ImageView>(R.id.ellipse_glow)
        ellipseGlow.startAnimation(scaleAnim)

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

