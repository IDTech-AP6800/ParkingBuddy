package com.example.parkingap6800.activities

import com.example.parkingap6800.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.GetDevicesAsync
import com.idtech.zsdk_client.SetAutoAuthenticateAsync
import com.idtech.zsdk_client.SetAutoCompleteAsync
import com.idtech.zsdk_client.StartTransactionAsync
import com.idtech.zsdk_client.StartTransactionResponseData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SwipeCardActivity : AppCompatActivity() {

    private var devices: List<String> = emptyList()
    private var connectedDeviceId: String? = null

    private lateinit var swipeArrow: ImageView
    private lateinit var swipeIndication: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        // Initialize views
        swipeArrow = findViewById(R.id.swipeArrow)
        swipeIndication = findViewById(R.id.swipeIndication)

        // Start animations
        startAnimations()

        // Start the swipe transaction process
        startSwipeCardTransaction()
    }

    private fun startAnimations() {
        // Swipe Indication Animations
        val moveDown = ObjectAnimator.ofFloat(swipeIndication, View.TRANSLATION_Y, 0f, 700f).apply {
            duration = 1500
        }

        val pauseDown = ObjectAnimator.ofFloat(swipeIndication, View.TRANSLATION_Y, 700f, 700f).apply {
            duration = 1000
        }

        val moveUp = ObjectAnimator.ofFloat(swipeIndication, View.TRANSLATION_Y, 700f, 0f).apply {
            duration = 500
        }

        val pauseUp = ObjectAnimator.ofFloat(swipeIndication, View.TRANSLATION_Y, 0f, 0f).apply {
            duration = 500
        }

        val swipeIndicationSet = AnimatorSet().apply {
            playSequentially(moveDown, pauseDown, moveUp, pauseUp)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    start() // Restart the animation loop
                }
            })
            start()
        }

        // Swipe Arrow Animation
        ObjectAnimator.ofFloat(swipeArrow, View.TRANSLATION_X, -20f, 0f).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }
    }

private fun startSwipeCardTransaction() {
    CoroutineScope(Dispatchers.IO).launch {
        // Enumerate devices
        if (enumerateDevices()) {
            connectedDeviceId = devices.firstOrNull()
        }

        connectedDeviceId?.let { deviceId ->
            // Enable auto-authenticate
            Client.SetAutoAuthenticateAsync(deviceId, true)
                .waitForCompletionWithTimeout(1000)

            // Enable auto-complete
            Client.SetAutoCompleteAsync(deviceId, true)
                .waitForCompletionWithTimeout(1000)

            // Define transaction parameters
            val amount = 0.1
            val amountOther = 0.0
            val transType: UByte = 0u
            val transTimeout: UByte = 100u
            val transInterfaceType: Int = 1 // 1 for MSR (Swipe)

            // Start the transaction
            val startTransCmd = Client.StartTransactionAsync(
                deviceId,
                amount, amountOther, transType, transTimeout,
                transInterfaceType.toUByte(), 3000
            )

            startTransCmd.waitForCompletion()

            // Check the transaction status
            val resultData: StartTransactionResponseData? = startTransCmd.getResultData()
            if (resultData != null) {
                // If transaction succeeds, navigate to ProcessingActivity
                withContext(Dispatchers.Main) {
                    navigateToProcessingActivity()
                }
            }
        }
    }
}

    private suspend fun enumerateDevices(): Boolean {
        return runCatching {
            val cmd = Client.GetDevicesAsync()
            cmd.waitForCompletionWithTimeout(3000)
            devices = cmd.devices
            devices.isNotEmpty()
        }.getOrDefault(false)
    }

    private fun navigateToProcessingActivity() {
        val intent = Intent(this, ProcessingActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "SwipeCardActivity"
    }
}
