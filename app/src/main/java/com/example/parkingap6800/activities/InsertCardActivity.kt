package com.example.parkingap6800.activities
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R


class InsertCardActivity : AppCompatActivity() {

    private lateinit var cardAnimation: AnimationDrawable
    private lateinit var swipeIndication: ImageView

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


        // OLD
//        // Animation functionality
//        // Find the ImageView
//        val imageView5 = findViewById<ImageView>(R.id.swipeIndication)
//
//        // Load animation from XML
//        val floatAnimation1 = AnimationUtils.loadAnimation(this, R.anim.float_up_down1)
//
//        // Start the animation
//        imageView5.startAnimation(floatAnimation1)
//
//        // Animation functionality - Down arrow (@+id/insertArrow)
//        // Find the ImageView
//        val insertarrow = findViewById<ImageView>(R.id.insertArrow)
//
//        // Load animation from XML
//        val floatAnimation2 = AnimationUtils.loadAnimation(this, R.anim.float_up_down2)
//        insertarrow.startAnimation(floatAnimation2)

        // Initialize the ImageView for swipe indication (this holds the animation)
        swipeIndication = findViewById(R.id.swipeIndication)

        // Load the animation drawable from the XML resource
        swipeIndication.setImageResource(R.drawable.insert_card_animation)

        // Initialize the AnimationDrawable from the ImageView's drawable
        cardAnimation = swipeIndication.drawable as AnimationDrawable
    }

    override fun onStart() {
        super.onStart()

        // Start the animation
        cardAnimation.start()
    }

    companion object {
        private const val TAG = "InsertCardActivity" // Define a tag for easier filtering in Logcat
    }
}