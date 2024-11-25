package com.example.parkingap6800.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R
import com.example.parkingap6800.databinding.ActivityMainBinding
import com.example.parkingap6800.viewmodels.MainViewModel
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.GetDevicesAsync

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Client.GetDevicesAsync()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set an OnClickListener on the root view to detect clicks anywhere on the screen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            // Intent to switch to ParkingInfoActivity
            val intent = Intent(this@MainActivity, ParkingInfoActivity::class.java)
            startActivity(intent)
        }

        // Initialize and fetch devices asynchronously
        Client.GetDevicesAsync()

        // Observe connection status and print to log
        viewModel.connectionStatus.observe(this) { status ->
            if (status == "Connected") {
                Log.d("ConnectionStatus", "The device is successfully connected!")
            } else {
                Log.d("ConnectionStatus", "Connection status: $status")
            }
        }

        // Trigger server connection
        viewModel.connectToServer("127.0.0.1", "42501", this)
    }
}
