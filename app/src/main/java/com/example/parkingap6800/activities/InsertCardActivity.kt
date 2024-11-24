package com.example.parkingap6800.activities
import com.example.parkingap6800.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity


class InsertCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        Log.d(TAG, "Home button created!");
        setContentView(R.layout.activity_insert)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        // Set an OnClickListener on the root view to detect clicks anywhere on the screen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener { // Intent to switch to Processing activity
            val intent = Intent(this@InsertCardActivity, ProcessingActivity::class.java)
            startActivity(intent)
        }


        // Home button functionality -

        // Find the home button imageview by ID

        val homeButton = findViewById<LinearLayout>(R.id.home_button)

        // Set OnCLickListener for the button
        homeButton.setOnClickListener { // Print to console when the button is clicked
            //                Log.d(TAG, "Home button clicked!");

            // Create an Intent to start MainActivity
            val i = Intent(this@InsertCardActivity, MainActivity::class.java)

            // optional: Clear activity stack to avoid multiple instances??
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

            // Start MainActivity
            startActivity(i)

            // Call finish() to close InsertCardActivity
            finish()
        }


        // Animation functionality
        // Find the ImageView
        val imageView5 = findViewById<ImageView>(R.id.swipeIndication)

        // Load animation from XML
        val floatAnimation1 = AnimationUtils.loadAnimation(this, R.anim.float_up_down1)

        // Start the animation
        imageView5.startAnimation(floatAnimation1)

        // Animation functionality - Down arrow (@+id/insertArrow)
        // Find the ImageView
        val insertarrow = findViewById<ImageView>(R.id.insertArrow)

        // Load animation from XML
        val floatAnimation2 = AnimationUtils.loadAnimation(this, R.anim.float_up_down2)
        insertarrow.startAnimation(floatAnimation2)
    }

    companion object {
        private const val TAG = "InsertCardActivity" // Define a tag for easier filtering in Logcat
    }
}