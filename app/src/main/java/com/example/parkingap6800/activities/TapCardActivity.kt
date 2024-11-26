package com.example.parkingap6800.activities

import android.os.Bundle
import android.widget.TextView
import com.example.parkingap6800.R
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.ParkingSession


class TapCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        val totalDue = ParkingSession.totalDue
        val totalDueTextView = findViewById<TextView>(R.id.totalDue)
        totalDueTextView.text = "Total due: $$totalDue"
        }

}