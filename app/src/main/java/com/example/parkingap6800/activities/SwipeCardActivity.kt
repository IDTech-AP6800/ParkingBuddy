package com.example.parkingap6800.activities
import com.example.parkingap6800.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class SwipeCardActivity : AppCompatActivity() {
    private var swipeArrow: ImageView? = null
    private var swipeIndication: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)
        swipeArrow = findViewById<ImageView>(R.id.swipeArrow)
        swipeIndication = findViewById<ImageView>(R.id.swipeIndication)
        startAnimations()
    }

    private fun startAnimations() {
        // Swipe Indication Animations
        val moveDown = ObjectAnimator.ofFloat(swipeIndication, "translationY", 0f, 350f)
        moveDown.duration = 1500
        val pauseDown = ObjectAnimator.ofFloat(swipeIndication, "translationY", 350f, 350f)
        pauseDown.duration = 1000
        val moveUp = ObjectAnimator.ofFloat(swipeIndication, "translationY", 350f, 0f)
        moveUp.duration = 500
        val pauseUp = ObjectAnimator.ofFloat(swipeIndication, "translationY", 0f, 0f)
        pauseUp.duration = 500
        val swipeIndicationSet = AnimatorSet()
        swipeIndicationSet.playSequentially(moveDown, pauseDown, moveUp, pauseUp)

        // Restart the animation
        swipeIndicationSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                swipeIndicationSet.start()
            }
        })
        swipeIndicationSet.start()

        // Swipe Arrow Animation
        val arrowAnimator = ObjectAnimator.ofFloat(swipeArrow, "translationX", -20f, 0f)
        arrowAnimator.duration = 1000 // 1 second
        arrowAnimator.repeatCount = ObjectAnimator.INFINITE
        arrowAnimator.repeatMode = ObjectAnimator.REVERSE
        arrowAnimator.start()
    }
}