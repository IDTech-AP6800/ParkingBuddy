package com.example.parkingap6800.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.GetDevicesAsync
import com.idtech.zsdk_client.SetAutoAuthenticateAsync
import com.idtech.zsdk_client.SetAutoCompleteAsync
import com.idtech.zsdk_client.StartTransactionAsync
import com.idtech.zsdk_client.StartTransactionResponseData
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
            // Enumerate devices
            if (enumerateDevices()) {
                connectedDeviceId = getDeviceId()
            }

            if (connectedDeviceId == null) {
                return@launch
            }

            // Enable auto-authenticate
            val authCmd = Client.SetAutoAuthenticateAsync(connectedDeviceId!!, true)
            authCmd.waitForCompletionWithTimeout(1000)

            // Enable auto-complete
            val completeCmd = Client.SetAutoCompleteAsync(connectedDeviceId!!, true)
            completeCmd.waitForCompletionWithTimeout(1000)

            // Define transaction parameters
            val amount = 0.1
            val amountOther = 0.0
            val transType: UByte = 0u
            val transTimeout: UByte = 100u
            val transInterfaceType: Int = 2 // 2 for CTLS (Tap)

            // Start the transaction
            val startTransCmd = Client.StartTransactionAsync(
                connectedDeviceId!!,
                amount, amountOther, transType, transTimeout,
                transInterfaceType.toUByte(), 3000
            )

            startTransCmd.waitForCompletion()

            // Check the transaction status
            val transStatus = startTransCmd.getCommandStatus()

            // Get the result data
            val resultData: StartTransactionResponseData? = startTransCmd.getResultData()
            if (resultData != null) {
                // If transaction succeeds, navigate to ProcessingActivity
                withContext(Dispatchers.Main) {
                    navigateToProcessingActivity()
                }
            }
        }
    }



    private suspend fun enumerateDevices(): Boolean {
        return try {
            val cmd = Client.GetDevicesAsync()
            val status = cmd.waitForCompletionWithTimeout(3000)
            devices = cmd.devices
            devices.forEachIndexed { i, info ->
            }
            devices.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    private fun getDeviceId(): String? {
        return try {
            if (devices.isNotEmpty()) {
                val deviceId = devices[0]
                deviceId
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun navigateToProcessingActivity() {
        val intent = Intent(this, ProcessingActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "TapCardActivity"
    }
}
