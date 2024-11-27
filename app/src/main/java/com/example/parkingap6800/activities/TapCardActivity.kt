package com.example.parkingap6800.activities

import android.animation.AnimatorSet
import android.os.Bundle
import android.widget.TextView
import com.example.parkingap6800.R
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.ParkingSession
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.View
import android.widget.ImageView


class TapCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        // Retrieve the total due amount from the ParkingSession singleton class
        val totalDue = ParkingSession.totalDue

        // Find the TextView responsible for displaying the total due amount in the layout
        val totalDueTextView = findViewById<TextView>(R.id.totalDue)

        // Set the text of the TextView to display the total due amount
        totalDueTextView.text = "Total due: $$totalDue"
        
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

        // Set an OnClickListener on the root view to detect clicks anywhere on the screen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            // Intent to switch to ParkingInfoActivity
            val intent = Intent(this@TapCardActivity, ProcessingActivity::class.java)
            startActivity(intent)
        }

    }

}