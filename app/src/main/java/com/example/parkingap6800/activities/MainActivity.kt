package com.example.parkingap6800.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an instance of the floating animation
        val floatAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.float_up_down1)


        // Set floating animation on Park Buddy logo ImageView
        val parkBuddyLogo = findViewById<ImageView>(R.id.park_buddy_logo)
        parkBuddyLogo.startAnimation(floatAnimation)

        // Set an OnClickListener on the root view to detect clicks anywhere on the screen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener { // Intent to switch to ParkingInfo activity
            val intent = Intent(this@MainActivity, ParkingInfoActivity::class.java)
            startActivity(intent)
        }
    }
}

