package com.example.parkingap6800.activities
import android.content.Intent
import com.example.parkingap6800.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class ProcessingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing)

        // Set an OnClickListener on the root view to detect clicks anywhere on the screen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener { // Intent to switch to PaymentFailed activity
            val intent = Intent(this@ProcessingActivity, PaymentFailedActivity::class.java)
            startActivity(intent)

            // Call finish() to close ProcessingActivity
            finish()
        }
    }
}