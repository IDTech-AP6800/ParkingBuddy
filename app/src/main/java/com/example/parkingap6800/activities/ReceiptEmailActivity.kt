package com.example.parkingap6800.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.parkingap6800.R

class ReceiptEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt_email)

        // This listener leads to the next page
        listenContinueButton()
    }

    private fun listenContinueButton() {
        val continueButton = findViewById<Button>(R.id.continueButton)
        continueButton.setOnClickListener {
            val intent = Intent(this@ReceiptEmailActivity, ReceiptSentActivity::class.java)
            startActivity(intent)
        }
    }
}