package com.example.parkingap6800.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.parkingap6800.R

class ReceiptPhoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt_phone)

        //This listener leads to the next page
        listenContinueButton()
    }

    private fun listenContinueButton() {
        val continueButton = findViewById<Button>(R.id.continueButton)
        continueButton.setOnClickListener {
            //Check if the phone number is valid
            val phoneNumber = findViewById<EditText>(R.id.phoneNumberInfoBox)
            val errorMessage = findViewById<View>(R.id.phoneErrorMessage) as TextView
            val phoneRegex = Regex("^(\\+\\d{1,3}[- ]?)?\\d{10}\$")

            if (!phoneRegex.matches(phoneNumber.text.toString().trim())){
                errorMessage.text = "Phone number needs to be 10 digits"
                errorMessage.setBackgroundColor(Color.parseColor("#CC000000"))
                listenErrorClick()
            }
            else {
                //Create and intent to navigate to the Receipt Sent Page
                val intent = Intent(this@ReceiptPhoneActivity, ReceiptSentActivity::class.java)
                startActivity(intent)
            }
        }
    }

    //Creates listener to listen to page being tapped after error image pops up
    private fun listenErrorClick() {
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            val errorMessage = findViewById<View>(R.id.phoneErrorMessage) as TextView
            errorMessage.text = ""
            errorMessage.setBackgroundColor(Color.parseColor("#00000000"))
        }
    }
}