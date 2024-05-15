package com.example.churchproject.ui.qr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.churchproject.R
import com.example.churchproject.databinding.ActivityScanBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class ScanActivity : AppCompatActivity() {
    lateinit var binding: ActivityScanBinding
    private lateinit var barcodeView: DecoratedBarcodeView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
// Check for camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }

        barcodeView = findViewById(R.id.barcode_scanner)
        barcodeView.decodeContinuous { result ->
            val intent = intent.apply {
                putExtra("SCAN_RESULT", result.text)
            }
            setResult(RESULT_OK, intent)
            finish()
        }

    }
    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }
}