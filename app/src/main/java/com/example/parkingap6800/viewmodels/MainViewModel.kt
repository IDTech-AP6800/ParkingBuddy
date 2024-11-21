package com.example.parkingap6800.viewmodels

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idtech.zsdk_client.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// ViewModel to manage server connection
class MainViewModel : ViewModel() {

    // LiveData to track connection status
    val connectionStatus = MutableLiveData<String>()

    // Function to connect to the payment server
    fun connectToServer(ip: String, port: String = "42501", activity: Activity) {
        connectionStatus.postValue("Connecting to server...")
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Attempt to connect to the server
                Client.autoReconnectToServer(activity, ip, port, "PaymentTest", "1.0", 1u, "")
                Client.waitUntilConnected()
                connectionStatus.postValue("Connected to $ip:$port")
            } catch (e: Exception) {
                connectionStatus.postValue("Connection failed: ${e.message}")
            }
        }
    }
}
