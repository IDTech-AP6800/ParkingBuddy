package com.example.parkingap6800.activities
import com. example.parkingap6800.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set an OnClickListener on the root view to detect clicks anywhere on the screen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener { // Intent to switch to ParkingInfo activity
            val intent = Intent(this@MainActivity, ParkingInfoActivity::class.java)
            startActivity(intent)
        }
    }
}

