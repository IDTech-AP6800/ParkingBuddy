package com.example.parkingap6800.activities

import android.os.Bundle
import android.widget.TextView
import com.example.parkingap6800.R
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.ParkingSession


class QrCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        // Retrieve the total due amount from the ParkingSession singleton class
        val totalDue = ParkingSession.totalDue

        // Find the TextView responsible for displaying the total due amount in the layout
        val totalDueTextView = findViewById<TextView>(R.id.totalDue)

        // Set the text of the TextView to display the total due amount
        totalDueTextView.text = "Total due: $$totalDue"
    }

}