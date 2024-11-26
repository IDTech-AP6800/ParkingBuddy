package com.example.parkingap6800.activities

import android.os.Bundle
import com.example.parkingap6800.R
import androidx.appcompat.app.AppCompatActivity


class QrCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)
    }

}