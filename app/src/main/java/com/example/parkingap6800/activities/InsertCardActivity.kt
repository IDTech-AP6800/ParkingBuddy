package com.example.parkingap6800.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.StartTransactionResponseData
import kotlinx.coroutines.*
import com.idtech.zsdk_client.*
import com.idtech.zsdk_client.api.StartEMVTransactionAsync

class InsertCardActivity : AppCompatActivity() {

    private var devices: List<String> = emptyList()
    private var connectedDeviceId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        // Set an OnClickListener on the root view to detect clicks anywhere on the screen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            val intent = Intent(this@InsertCardActivity, ProcessingActivity::class.java)
            startActivity(intent)
        }

        // Animation functionality
        val imageView5 = findViewById<ImageView>(R.id.swipeIndication)
        val floatAnimation1 = AnimationUtils.loadAnimation(this, R.anim.float_up_down1)
        imageView5.startAnimation(floatAnimation1)

        val insertarrow = findViewById<ImageView>(R.id.insertArrow)
        val floatAnimation2 = AnimationUtils.loadAnimation(this, R.anim.float_up_down2)
        insertarrow.startAnimation(floatAnimation2)

        // Start the EMV transaction process
        startEMVTransaction()
    }

    private fun startEMVTransaction() {
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
            val transInterfaceType: Int = 4 // 4 for EMV (Insert)

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
        private const val TAG = "InsertCardActivity"
    }
}
