package com.example.parkingap6800.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R

class PaymentSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        // Find the ellipse ImageView by its ID
        var ellipseGlow = findViewById<ImageView>(R.id.ellipse_glow)

        // Create the scale and alpha animations
        val scaleAnimatorX = ObjectAnimator.ofFloat(ellipseGlow, "scaleX", 1f, 1.2f)
        val scaleAnimatorY = ObjectAnimator.ofFloat(ellipseGlow, "scaleY", 1f, 1.2f)
        val alphaAnimator = ObjectAnimator.ofFloat(ellipseGlow, "alpha", 0.4f, 1f)

        // Set the repeat settings for each individual animator
        scaleAnimatorX.repeatCount = ObjectAnimator.INFINITE
        scaleAnimatorX.repeatMode = ObjectAnimator.REVERSE

        scaleAnimatorY.repeatCount = ObjectAnimator.INFINITE
        scaleAnimatorY.repeatMode = ObjectAnimator.REVERSE

        alphaAnimator.repeatCount = ObjectAnimator.INFINITE
        alphaAnimator.repeatMode = ObjectAnimator.REVERSE

        // Combine the animations into an AnimatorSet
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleAnimatorX, scaleAnimatorY, alphaAnimator)

        // Set the duration for the animation
        animatorSet.duration = 1200

        // Start the animation
        animatorSet.start()

        //QR receipt option event listener
        val qrOption = findViewById<LinearLayout>(R.id.qr_code_option)
        qrOption.setOnClickListener {
            val intent = Intent(this@PaymentSuccessActivity, ReceiptQrActivity::class.java)
            startActivity(intent)
        }

        //Email receipt option event listener
        val emailOption = findViewById<LinearLayout>(R.id.email_option)
        emailOption.setOnClickListener {
            val intent = Intent(this@PaymentSuccessActivity, ReceiptEmailActivity::class.java)
            startActivity(intent)
        }

        //Phone number receipt option event listener
        val smsOption = findViewById<LinearLayout>(R.id.sms_option)
        smsOption.setOnClickListener {
            val intent = Intent(this@PaymentSuccessActivity, ReceiptPhoneActivity::class.java)
            startActivity(intent)
        }

        //Phone number receipt option event listener
        val noReceiptOption = findViewById<LinearLayout>(R.id.no_receipt)
        noReceiptOption.setOnClickListener {
            val intent = Intent(this@PaymentSuccessActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
