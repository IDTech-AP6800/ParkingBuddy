package com.example.parkingap6800.activities
import com.example.parkingap6800.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity


class PaymentOptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_options)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        //Insert Option Listener
        val insertOption = findViewById<LinearLayout>(R.id.insert_option)
        insertOption.setOnClickListener {
            val intent = Intent(this@PaymentOptionsActivity, InsertCardActivity::class.java)
            startActivity(intent)
        }

        //Swipe Option Listener
        val swipeOption = findViewById<LinearLayout>(R.id.swipe_option)
        swipeOption.setOnClickListener {
            val intent = Intent(this@PaymentOptionsActivity, SwipeCardActivity::class.java)
            startActivity(intent)
        }

        //Tap Option Listener
        val tapOption = findViewById<LinearLayout>(R.id.tap_option)
        tapOption.setOnClickListener {
            Log.d("PaymentOptionsActivity", "Tap option clicked")
            val intent = Intent(this@PaymentOptionsActivity, TapCardActivity::class.java)
            startActivity(intent)
        }
    }
}