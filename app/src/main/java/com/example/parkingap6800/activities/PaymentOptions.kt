package com.example.parkingap6800.activities
import com.example.parkingap6800.R
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity


class PaymentOptions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_options)

        //Insert Option Listener
        val insertOption = findViewById<LinearLayout>(R.id.insert_option)
        insertOption.setOnClickListener {
            val intent = Intent(this@PaymentOptions, InsertCard::class.java)
            startActivity(intent)
        }

        //Swipe Option Listener
        val swipeOption = findViewById<LinearLayout>(R.id.swipe_option)
        swipeOption.setOnClickListener {
            val intent = Intent(this@PaymentOptions, SwipeCardActivity::class.java)
            startActivity(intent)
        }
    }
}