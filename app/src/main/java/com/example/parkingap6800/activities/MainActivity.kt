package com.example.parkingap6800.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingap6800.R
import com.example.parkingap6800.databinding.ActivityMainBinding
import com.example.parkingap6800.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe connection status and update the TextView
        viewModel.connectionStatus.observe(this) { status ->
            binding.tvStatus.text = status
        }

        // Trigger server connection
        viewModel.connectToServer("192.168.1.1", "42501", this)
    }
}
