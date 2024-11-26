package com.example.parkingap6800.activities

import com.example.parkingap6800.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.example.parkingap6800.ParkingSession

class SwipeCardActivity : AppCompatActivity() {

    private lateinit var swipeArrow: ImageView
    private lateinit var swipeIndication: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        val totalDue = ParkingSession.totalDue
        val totalDueTextView = findViewById<TextView>(R.id.totalDue)
        totalDueTextView.text = "Total due: $$totalDue"

        // Initialize views
        swipeArrow = findViewById(R.id.swipeArrow)
        swipeIndication = findViewById(R.id.swipeIndication)

        // Start animations
        startAnimations()
    }

    private fun startAnimations() {
        // Swipe Indication Animations
        val moveDown = ObjectAnimator.ofFloat(swipeIndication, View.TRANSLATION_Y, 0f, 400f).apply {
            duration = 700
        }

        val pauseDown = ObjectAnimator.ofFloat(swipeIndication, View.TRANSLATION_Y, 400f, 400f).apply {
            duration = 500
        }

        val moveUp = ObjectAnimator.ofFloat(swipeIndication, View.TRANSLATION_Y, 400f, 0f).apply {
            duration = 400
        }

        val pauseUp = ObjectAnimator.ofFloat(swipeIndication, View.TRANSLATION_Y, 0f, 0f).apply {
            duration = 500
        }

        val swipeIndicationSet = AnimatorSet().apply {
            playSequentially(moveDown, pauseDown, moveUp, pauseUp)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    start() // Restart the animation loop
                }
            })
            start()
        }

        // Swipe Arrow Animation
        ObjectAnimator.ofFloat(swipeArrow, View.TRANSLATION_X, -10f, -0f).apply {
            duration = 2000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }
    }
}
