package com.example.parkingap6800.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import com.example.parkingap6800.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ProcessingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing)

        // Find the green shadow background ImageView by its ID
        var ellipseGlow = findViewById<ImageView>(R.id.greenProcessingBackground)

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
