package com.example.parkingap6800.activities

import android.widget.TextView

//This class does the calculations used in ParkingInfo
class ParkingInfoCalculation {
    // Feel free to change these variables as you'd like
    private val maxHours = 24
    private val minHours = 1
    private val maxMinutes = 30
    private val minMinutes = 0
    private val hourRate = 4f // Number of dollars per hour
    private val minuteRate = 2f // Number of dollars per minute intervals
    private val hourIntervals = 1 //By how much the hours increase
    private val minuteIntervals = 30 //By how much the minutes increase
    private var hours = 1
    private var minutes = 0

    //Increases the number of hours and changes the TextView to reflect it
    fun increaseHours(hoursText: TextView) {
        if (hours < maxHours) hours += hourIntervals else hours =
            minHours // Reset when you hit the max
        hoursText.text = hours.toString()
    }

    //Decreases the number of hours and changes the TextView to reflect it
    fun decreaseHours(hoursText: TextView) {
        if (hours > minHours) hours -= hourIntervals else hours =
            maxHours // Reset when you hit the min
        hoursText.text = hours.toString()
    }

    //Increases the number of minutes and changes the TextView to reflect it
    fun increaseMinutes(minutesText: TextView, hoursText: TextView) {
        if (minutes < maxMinutes) minutes += minuteIntervals else {
            minutes = minMinutes // Reset when you hit the max
            increaseHours(hoursText)
        }
        minutesText.text = minutes.toString()
    }

    //Decreases the number of minutes and changes the TextView to reflect it
    fun decreaseMinutes(minutesText: TextView, hoursText: TextView) {
        if (minutes > minMinutes) minutes -= minuteIntervals else {
            minutes = maxMinutes // Reset when you hit the min
            decreaseHours(hoursText)
        }
        minutesText.text = minutes.toString()
    }

    fun updateParkingFee(t: TextView) {
        val total = String.format(
            "Total due: $%.2f",
            hours * hourRate + minutes / maxMinutes * minuteRate
        )
        t.text = total
    }
}