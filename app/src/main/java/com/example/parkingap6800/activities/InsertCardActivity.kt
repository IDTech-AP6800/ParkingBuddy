package com.example.parkingap6800.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
            try {

                // Enumerate devices
                if (enumerateDevices()) {
                    connectedDeviceId = getDeviceId()
                }

                if (connectedDeviceId == null) {
                    Log.e(TAG, "No connected device available.")
                    return@launch
                }

                // Enable auto-authenticate
                try {
                    val authCmd = Client.SetAutoAuthenticateAsync(connectedDeviceId!!, true)
                    val authStatus = authCmd.waitForCompletionWithTimeout(1000)
                    Log.d(TAG, "Enable Auto Authenticate: ${authStatus.name}")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to enable auto-authenticate", e)
                }

                // Enable auto-complete
                try {
                    val completeCmd = Client.SetAutoCompleteAsync(connectedDeviceId!!, true)
                    val completeStatus = completeCmd.waitForCompletionWithTimeout(1000)
                    Log.d(TAG, "Enable Auto Complete: ${completeStatus.name}")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to enable auto-complete", e)
                }

                // Define transaction parameters
                val amount = 0.1
                val amountOther = 0.0
                val transType: UByte = 0u
                val transTimeout: UByte = 100u
                val transInterfaceType: Int = 1 // 1 for MSR, 2 for CTLS, and 4 for EMV

                // Start the transaction
                try {
                    val startTransCmd = Client.StartTransactionAsync(
                        connectedDeviceId!!,
                        amount, amountOther, transType, transTimeout,
                        transInterfaceType.toUByte(), 3000
                    )

                    startTransCmd.waitForCompletion()

                    // Check the transaction status
                    val transStatus = startTransCmd.getCommandStatus()
                    Log.d(TAG, "Transaction Status: ${transStatus.name}")

                    // Get the result data
                    val resultData: StartTransactionResponseData? = startTransCmd.getResultData()
                    if (resultData != null) {
                        Log.d(TAG, "Transaction Response Type: ${resultData.respType}")
                        Log.d(TAG, "Transaction Attributes: ${resultData.attribute}")
                        Log.d(TAG, "Card Data: ${resultData.cardData}")
                    } else {
                        Log.e(TAG, "Transaction failed or result data is null!")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Transaction execution failed", e)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in startEMVTransaction", e)
            }
        }
    }

    private suspend fun enumerateDevices(): Boolean {
        return try {
            val cmd = Client.GetDevicesAsync()
            val status = cmd.waitForCompletionWithTimeout(3000)
            devices = cmd.devices
            Log.d(TAG, "GetDevices Status: $status")
            devices.forEachIndexed { i, info ->
                Log.d(TAG, "DeviceIndex $i; DeviceId: $info")
            }
            devices.isNotEmpty()
        } catch (e: Exception) {
            Log.e(TAG, "Error enumerating devices", e)
            false
        }
    }

    private fun getDeviceId(): String? {
        return try {
            if (devices.isNotEmpty()) {
                val deviceId = devices[0]
                Log.d(TAG, "Device ID retrieved: $deviceId")
                deviceId
            } else {
                Log.e(TAG, "No devices found!")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving device ID", e)
            null
        }
    }

    companion object {
        private const val TAG = "InsertCardActivity" // Define a tag for easier filtering in Logcat
    }
}
