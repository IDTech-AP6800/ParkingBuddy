package com.example.parkingap6800.activities
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R


class InsertCardActivity : AppCompatActivity() {

    // Declare a variable of type AnimationDrawable to hold the frame-by-frame animation
    // which will be initialized later by retrieving the drawable from the ImageView.
    private lateinit var cardAniVar: AnimationDrawable

    // Declare a variable of type ImageView to reference the ImageView widget
    // (which will be initialized later by finding it in the layout).
    private lateinit var iViewVar: ImageView

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


        // Initialize iViewVar by finding the ImageView inside the XML with ID cardIcon
        iViewVar = findViewById(R.id.cardIcon)

        // Initialize cardAniVar by getting the drawable from the ImageView
        cardAniVar = iViewVar.drawable as AnimationDrawable
    }

    override fun onStart() {
        super.onStart()

        // Start the animation
        cardAniVar.start()
    }

    companion object {
        private const val TAG = "InsertCardActivity" // Define a tag for easier filtering in Logcat
    }
}