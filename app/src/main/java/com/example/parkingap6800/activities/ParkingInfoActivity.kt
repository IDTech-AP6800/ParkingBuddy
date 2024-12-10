package com.example.parkingap6800.activities

import com.example.parkingap6800.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.ParkingSession

class ParkingInfoActivity : AppCompatActivity() {
    private val calc: ParkingInfoCalculation = ParkingInfoCalculation()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_info)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        //Setting up the event listeners
        listenIncreaseHours()
        listenDecreaseHours()
        listenIncreaseMinutes()
        listenDecreaseMinutes()
        //This listener leads to the next page
        listenContinueButton()

        setLicensePlateInputFilter();
    }
    // Set input filter to enforce 7 character limit
    private fun setLicensePlateInputFilter() {
        val licensePlateInfo = findViewById<EditText>(R.id.licensePlateInfo)
        val lengthFilter: InputFilter = LengthFilter(7)
        licensePlateInfo.filters = arrayOf(lengthFilter)
    }

    //Creates listener for increasing hours
    private fun listenIncreaseHours() {
        val upButtonHours = findViewById<View>(R.id.upButtonHours) as ImageButton
        upButtonHours.setOnClickListener {
            val hoursText = findViewById<View>(R.id.parkingDurationHours) as TextView
            calc.increaseHours(hoursText)
            updateParkingFee()
        }
    }

    //Creates listener for decreasing hours
    private fun listenDecreaseHours() {
        val downButtonHours = findViewById<View>(R.id.downButtonHours) as ImageButton
        downButtonHours.setOnClickListener {
            val hoursText = findViewById<View>(R.id.parkingDurationHours) as TextView
            calc.decreaseHours(hoursText)
            updateParkingFee()
        }
    }

    //Creates listener for increasing minutes
    private fun listenIncreaseMinutes() {
        val upButtonMinutes = findViewById<View>(R.id.upButtonMinutes) as ImageButton
        upButtonMinutes.setOnClickListener {
            val hoursText = findViewById<View>(R.id.parkingDurationHours) as TextView
            val minutesText =
                findViewById<View>(R.id.parkingDurationMinutes) as TextView
            calc.increaseMinutes(minutesText, hoursText)
            updateParkingFee()
        }
    }

    //Creates listener for decreasing minutes
    private fun listenDecreaseMinutes() {
        val downButtonMinutes = findViewById<View>(R.id.downButtonMinutes) as ImageButton
        downButtonMinutes.setOnClickListener {
            val hoursText = findViewById<View>(R.id.parkingDurationHours) as TextView
            val minutesText =
                findViewById<View>(R.id.parkingDurationMinutes) as TextView
            calc.decreaseMinutes(minutesText, hoursText)
            updateParkingFee()
        }
    }

    //Creates listener for the continue button
    private fun listenContinueButton() {
        val continueButton = findViewById<Button>(R.id.continueButton)
        continueButton.setOnClickListener {
            //Check if license plate is valid
            val licensePlate = findViewById<View>(R.id.licensePlateInfo) as TextView
            val errorMessage = findViewById<View>(R.id.errorMessage) as TextView
            if (licensePlate.text.length != 7) {
                errorMessage.text = "License plate needs to be 7 characters"
                errorMessage.setBackgroundColor(Color.parseColor("#CC000000"))
                listenErrorClick()
            } else {

                val parkingTotal = findViewById<View>(R.id.totalDue) as TextView
                val totalDueString = parkingTotal.text.toString()
                    .replace("Total due: $", "")
                    .trim()

                ParkingSession.totalDue = totalDueString.toDouble()

                // Create an Intent to navigate to the PaymentOptions activity
                val intent = Intent(this@ParkingInfoActivity, PaymentOptionsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    //Creates listener to listen to page being tapped after error image pops up
    private fun listenErrorClick() {
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            val errorMessage = findViewById<View>(R.id.errorMessage) as TextView
            errorMessage.text = ""
            errorMessage.setBackgroundColor(Color.parseColor("#00000000"))
        }
    }

    //Updates the parking fee
    private fun updateParkingFee() {
        val parkingTotal = findViewById<View>(R.id.totalDue) as TextView
        calc.updateParkingFee(parkingTotal)
    }
}