package com.example.parkingap6800.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R
import com.example.parkingap6800.databinding.ActivityMainBinding
import com.example.parkingap6800.viewmodels.MainViewModel
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Client.GetDevicesAsync()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe connection status and update the TextView
        viewModel.connectionStatus.observe(this) { status ->
            binding.tvStatus.text = status
        }

        // Trigger server connection
        viewModel.connectToServer("127.0.0.1", "42501", this)
    }
}
