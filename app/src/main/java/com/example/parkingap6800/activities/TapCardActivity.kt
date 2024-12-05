package com.example.parkingap6800.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
                    Log.e(TAG, "No connected device available.")
                    showTransactionError("No device connected")
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
                    Log.d(TAG, "Transaction completed successfully.")
                    moveToProcessingPage()
                } else {
                    // Handle transaction failure
                    Log.e(TAG, "Transaction failed: ${status.name}")
                    showTransactionError("Transaction failed: ${status.name}")
                }
            } catch (e: Exception) {
                // Handle error during the transaction
                Log.e(TAG, "Transaction error: ${e.message}", e)
                showTransactionError("Transaction error: ${e.message}")
            }
        }
    }

    private fun moveToProcessingPage() {
        // Switch to ProcessingActivity
        CoroutineScope(Dispatchers.Main).launch {
            val intent = Intent(this@TapCardActivity, ProcessingActivity::class.java)
            startActivity(intent)
            finish() // Optionally finish this activity to prevent going back
        }
    }

    private fun showTransactionError(message: String) {
        // Handle transaction error
        CoroutineScope(Dispatchers.Main).launch {
            // Show a message to the user
            Log.e(TAG, "Transaction Error: $message")
            // Optionally, navigate to an error page or display a dialog
        }
    }

    private suspend fun enumerateDevices(): Boolean {
        return try {
            val cmd = Client.GetDevicesAsync()
            cmd.waitForCompletionWithTimeout(3000)
            devices = cmd.devices
            devices.isNotEmpty()
        } catch (e: Exception) {
            Log.e(TAG, "Error enumerating devices: ${e.message}", e)
            false
        }
    }

    private fun getDeviceId(): String? {
        return try {
            if (devices.isNotEmpty()) {
                devices[0]
            } else {
                Log.e(TAG, "No devices found!")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving device ID: ${e.message}", e)
            null
        }
    }

    companion object {
        private const val TAG = "TapCardActivity"
    }
}
