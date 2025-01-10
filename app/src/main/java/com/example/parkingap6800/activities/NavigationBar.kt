package com.example.parkingap6800.activities

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R
import com.idtech.zsdk_client.CancelTransactionAsync
import com.idtech.zsdk_client.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NavigationBar(activity: AppCompatActivity) {

    // Initialize "homeButton" as an ImageView representing the home icon
    private val homeButton: ImageView = activity.findViewById<LinearLayout>(R.id.home_button)
        .findViewById(R.id.home_button_image)

    // Initialize "backButton" as an ImageView representing the back icon
    private val backButton: ImageView = activity.findViewById<LinearLayout>(R.id.back_button)
        .findViewById(R.id.back_button_image)

    // Initialize "helpButton" as an ImageView representing the help icon
    private val helpButton: ImageView = activity.findViewById<LinearLayout>(R.id.help_button)
        .findViewById(R.id.help_button_image)

    // Define a constant string "TAG" for easier filtering in Logcat
    companion object {
        private const val TAG = "navTest"
    }

    // Run this code upon instance of class creation
    init {
        Log.d(TAG, "Nav bar is loaded!!")
        homeButton.setOnClickListener {
            // Create an Intent to start MainActivity
            val intent = Intent(activity, MainActivity::class.java)

            // Clear activity stack to avoid multiple instances
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

            // Start MainActivity
            activity.startActivity(intent)

            // Call finish() to close the current activity
            activity.finish()
        }

        backButton.setOnClickListener {
            // Handle back navigation
            activity.onBackPressedDispatcher.onBackPressed()
        }

        helpButton.setOnClickListener{
            val intent = Intent(activity, HelpActivity::class.java)
            activity.startActivity(intent)
        }
    }
}