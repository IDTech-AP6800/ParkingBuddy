package com.example.parkingap6800.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R

class PaymentFailedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_failed)

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
        animatorSet.duration = 800

        // Start the animation
        animatorSet.start()
    }
}