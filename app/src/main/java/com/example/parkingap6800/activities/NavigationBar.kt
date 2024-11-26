package com.example.parkingap6800.activities

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R

class NavigationBar(activity: AppCompatActivity) {

    // Initialize "homeButton" as an ImageView representing the home icon
    private val homeButton: ImageView = activity.findViewById<LinearLayout>(R.id.home_button)
        .findViewById(R.id.home_button_image)

    // Initialize "backButton" as an ImageView representing the back icon
    private val backButton: ImageView = activity.findViewById<LinearLayout>(R.id.back_button)
        .findViewById(R.id.back_button_image)

    // Initialize "accessibilityButton" as an ImageView representing the accessibility icon
    private val accessibilityButton: ImageView = activity.findViewById<LinearLayout>(R.id.accessibility_button)
        .findViewById(R.id.accessibility_button_image)

    // Define a constant string "TAG" for easier filtering in Logcat
    companion object {
        private const val TAG = "navTest"
    }

    // Run this code upon instance of class creation
    init {
        Log.d(TAG, "Nav bar is loaded!!")

        // Set up OnClickListener for home button
        homeButton.setOnClickListener {
            // Print to console when button is clicked (optional for debugging)
             Log.d(TAG, "Home button clicked!")

            // Create an Intent to start MainActivity
            val intent = Intent(activity, MainActivity::class.java)

            // Clear activity stack to avoid multiple instances
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

            // Start MainActivity
            activity.startActivity(intent)

            // Call finish() to close the current activity
            activity.finish()
        }

        // Set up OnClickListener for back button
        backButton.setOnClickListener {
            // Print to console when button is clicked (optional for debugging)
            Log.d(TAG, "Back button clicked!")

            // Handle back navigation
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }
}