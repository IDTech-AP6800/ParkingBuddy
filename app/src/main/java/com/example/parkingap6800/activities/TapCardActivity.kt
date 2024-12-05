package com.example.parkingap6800.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.*
import com.idtech.zsdk_client.api.ActivateCtlsTransactionAsync
import kotlinx.coroutines.*

class TapCardActivity : AppCompatActivity() {

    private var devices: List<String> = emptyList()
    private var connectedDeviceId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap)

        // Initialize the NavigationBar class to handle navigation bar functionality
        NavigationBar(this)

        // Set an OnClickListener on the root view to detect clicks anywhere on the screen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            val intent = Intent(this@TapCardActivity, ProcessingActivity::class.java)
            startActivity(intent)
        }

        // Start the tap transaction process
        startTapTransaction()
    }

    private fun startTapTransaction() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Enumerate devices
                if (enumerateDevices()) {
                    connectedDeviceId = getDeviceId()
                }

                if (connectedDeviceId == null) {
                    // Handle case where no device is connected
                    return@launch
                }

                // Define transaction parameters
                val timeout = 30.toUByte() // 30 seconds timeout
                val tlvData = arrayOf<UByte>(
                    0xdfu, 0xefu, 0x3cu, 0x03u, 0x01u, 0x00u, 0x60u // Example TLV data
                )

                // Start the contactless transaction
                val cmd = Client.ActivateCtlsTransactionAsync(
                    connectedDeviceId!!, timeout, tlvData
                )
                cmd.waitForCompletionWithTimeout(10000) // Wait for the transaction to complete

                // Check the transaction status
                val status = cmd.getCommandStatus()

                if (status.name == "Completed") {
                    // Transaction completed successfully
                    moveToPaymentSuccessPage()
                } else {
                    // Handle transaction failure
                    showTransactionError()
                }
            } catch (e: Exception) {
                // Handle error during the transaction
            }
        }
    }

    private fun moveToPaymentSuccessPage() {
        // Switch to PaymentSuccessActivity
        CoroutineScope(Dispatchers.Main).launch {
            val intent = Intent(this@TapCardActivity, PaymentSuccessActivity::class.java)
            startActivity(intent)
            finish() // Optionally finish this activity to prevent going back
        }
    }

    private fun showTransactionError() {
        // You can handle transaction errors here, for example:
        CoroutineScope(Dispatchers.Main).launch {
            // Show a message or navigate to an error page
        }
    }

    private suspend fun enumerateDevices(): Boolean {
        return try {
            val cmd = Client.GetDevicesAsync()
            cmd.waitForCompletionWithTimeout(3000)
            devices = cmd.devices
            devices.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    private fun getDeviceId(): String? {
        return try {
            if (devices.isNotEmpty()) {
                devices[0]
            } else null
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val TAG = "TapCardActivity"
    }
}
