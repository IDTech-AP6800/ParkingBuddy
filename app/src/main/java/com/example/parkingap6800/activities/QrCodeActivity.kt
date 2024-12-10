package com.example.parkingap6800.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.parkingap6800.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import com.example.parkingap6800.ParkingSession
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode


// Code from the Android Studio CameraX tutorial is used here as well
// as references to SDK tutorial
class QrCodeActivity : AppCompatActivity() {

    lateinit var barcodeScanner : BarcodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
            Log.e(TAG, "All Permissions Granted")
        } else {
            requestPermissions()
        }

        // Initialize the NavigationBar class to handle the navigation bar functionality
        NavigationBar(this)

        // Retrieve the total due amount from the ParkingSession singleton class
        val totalDue = ParkingSession.totalDue

        // Find the TextView responsible for displaying the total due amount in the layout
        val totalDueTextView = findViewById<TextView>(R.id.totalDue)

        // Set the text of the TextView to display the total due amount
        totalDueTextView.text = "Total due: $$totalDue"

        // Set an OnClickListener on the root view to detect clicks anywhere on the screen
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            // Intent to switch to ParkingInfoActivity
            val intent = Intent(this@QrCodeActivity, ProcessingActivity::class.java)
            startActivity(intent)
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        {
                permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(baseContext, "Permission request denied",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                startCamera()
            }
        }
    private fun startCamera() {
        val cameraController = LifecycleCameraController(baseContext)
        val previewView: PreviewView = findViewById(R.id.viewFinder)

        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        barcodeScanner = BarcodeScanning.getClient(options)

        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(this),
            MlKitAnalyzer(
                listOf(barcodeScanner),
                COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(this)
            ) { result: MlKitAnalyzer.Result? ->
                val barcodeResults =
                    result?.getValue(barcodeScanner)
                val barcodeValue =
                    barcodeResults?.getOrNull(0)?.displayValue
                val barcodeFormat =
                    barcodeResults?.getOrNull(0)?.format
                if (barcodeValue != null) {
                    //If it has a value, that is it scanner
                    Log.d(TAG, "startCamera:\ncodeValue:$barcodeValue" +
                            "\nbarcodeFormat:$barcodeFormat")
                    val intent = Intent(this@QrCodeActivity, ProcessingActivity::class.java)
                    startActivity(intent)
                }
            }

            )

        cameraController.bindToLifecycle(this)
        previewView.setController(cameraController)

        /* The back camera is the one for qr codes
           If you want to use the front camera, change the selector
           to use DEFAULT_FRONT_CAMERA */
        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    }
    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        barcodeScanner.close()
    }

    companion object {
        private val TAG = QrCodeActivity::class.qualifiedName
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA
            ).toTypedArray()
    }

}