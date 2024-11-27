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
import com.idtech.zsdk_client.ClientCommandStatus
import com.idtech.zsdk_client.StartTransactionResponseData
import kotlinx.coroutines.*
import com.idtech.zsdk_client.*

class InsertCardActivity : AppCompatActivity() {

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

            // Enable auto-authenticate
            val authCmd = Client.SetAutoAuthenticateAsync(connectedDeviceId, true)
            val authStatus = authCmd.waitForCompletionWithTimeout(1000)
            Log.d(TAG, "Enable Auto Authenticate: ${authStatus.name}")

            // Enable auto-complete
            val completeCmd = Client.SetAutoCompleteAsync(connectedDeviceId, true)
            val completeStatus = completeCmd.waitForCompletionWithTimeout(1000)
            Log.d(TAG, "Enable Auto Complete: ${completeStatus.name}")

            // Define transaction parameters
            val amount = 0.1
            val amountOther = 0.0
            val transType: UByte = 0u // 0x00 = Purchase
            val transTimeout = 100
            val transactionTimeoutMilli = 10000L

            // Start the transaction
            val startTransCmd = Client.StartEMVTransactionAsync(
                connectedDeviceId,
                amount,
                amountOther,
                transType,
                transTimeout,
                false,
                transactionTimeoutMilli
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
        }
    }

    companion object {
        private const val TAG = "InsertCardActivity" // Define a tag for easier filtering in Logcat
    }
}
