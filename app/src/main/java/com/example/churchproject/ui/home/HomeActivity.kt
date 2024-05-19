package com.example.churchproject.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.churchproject.R
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.RequestAttendance
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ActivityHomeBinding
import com.example.churchproject.ui.bible.BibleActivity
import com.example.churchproject.ui.event.EventActivity
import com.example.churchproject.ui.jadwal.JadwalActivity
import com.example.churchproject.ui.loginsignup.LoginSignupActivity
import com.example.churchproject.ui.loginsignup.LoginSignupViewModel
import com.example.churchproject.ui.qr.AttendanceActivity
import com.example.churchproject.ui.qr.ScanActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.tasks.Task

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private val viewModel: LoginSignupViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private var email = ""
    private lateinit var scanActivityResultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getToken().observe(this) { it ->
            if (it == "" || it == null) {
                val intent = Intent(this, LoginSignupActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                homeViewModel.getUserData(Helper.getEmail(it))?.observe(this) {
                    when (it) {
                        is Resource.Success -> {
                            showLoading(false)
                            it.data?.let {
                                if (it.status == "success") {
                                    email = it.user.email
                                    binding.tvName.text = it.user.name
                                    binding.tvEmail.text = it.user.email
                                    binding.tvRole.text = it.user.role.capitalize()
                                }

                            }
                        }

                        is Resource.Loading -> {
                            showLoading(true)
                        }

                        is Resource.Error -> {
                            showLoading(false)
                        }
                    }

                }
            }
        }

        binding.btLogout.setOnClickListener {
            showConfirmDialog()
        }

        binding.cvJadwal.setOnClickListener {
            val intent = Intent(this, JadwalActivity::class.java)
            startActivity(intent)
        }

        binding.cvKegiatan.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            startActivity(intent)
        }

        binding.cvBible.setOnClickListener {
            val intent = Intent(this, BibleActivity::class.java)
            startActivity(intent)
        }

        scanActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intent = result.data
                    val scanResult = intent?.getStringExtra("SCAN_RESULT")
                    scanResult?.let {
                        homeViewModel.addAttendance(RequestAttendance(email, it)).observe(this) {
                            when (it) {
                                is Resource.Loading -> {
                                    showLoading(true)
                                }

                                is Resource.Success -> {
                                    showLoading(false)
                                    it.data?.let {
                                        if (it.status == "success") {
                                            startActivity(
                                                Intent(
                                                    this,
                                                    AttendanceActivity::class.java
                                                )
                                            )
                                        }
                                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                                    }

                                }

                                is Resource.Error -> {
                                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                                    showLoading(false)
                                }
                            }
                        }
                    }
                }
            }

        binding.btQr.setOnClickListener {
            getMyLocation()
        }

        binding.btAttendance.setOnClickListener {
            val intent = Intent(this, AttendanceActivity::class.java)
            startActivity(intent)
        }


    }

    private fun isPremissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getDeviceLocation()
            }else{
                Toast.makeText(
                    this,
                    "Presensi membutuhkan ijin lokasi",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getDeviceLocation()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getDeviceLocation() {
        try {
            lateinit var locationCallback: LocationCallback
            var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

            var locationRequest: LocationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 10000 // 10 seconds
                fastestInterval = 5000 // 5 seconds
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation
                    showLoading(false)
                    if (location != null) {
                        handleLocation(location)
                        fusedLocationClient.removeLocationUpdates(locationCallback)
                    } else {
                        Toast.makeText(this@HomeActivity, "Tidak ada lokasi saat ini", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            if (isPremissionGranted()) {
                var locationManager: LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (!hasGps) {
                    Toast.makeText(
                        this,
                        "Nyalakan GPS anda",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    val locationResult: Task<Location> =
                        LocationServices.getFusedLocationProviderClient(this).lastLocation
                    locationResult.addOnSuccessListener {
                        if (it != null) {
                            handleLocation(it)
                            fusedLocationClient.removeLocationUpdates(locationCallback)
                        } else {
                            showLoading(true)
                            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
                        }
                    }
                }
            } else {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        } catch (e: SecurityException) {
            Log.e("error", e.message, e)
        }
    }

    private fun handleLocation(it:Location){
        if(Helper.checkRadius(it)){
            val intent = Intent(this, ScanActivity::class.java)
            scanActivityResultLauncher.launch(intent)
        }else{
            Toast.makeText(
                this,
                "Anda sedang tidak dalam radius lokasi acara",
                Toast.LENGTH_SHORT
            ).show()
        }
    }



    private fun showConfirmDialog() {
        val builder = AlertDialog.Builder(this)
        val mConfirmDialog = builder.create()
        builder.setTitle(getString(R.string.keluar))
        builder.setMessage(getString(R.string.yakin_ingin_keluar))
        builder.create()

        builder.setPositiveButton(getString(R.string.ya)) { _, _ ->
            viewModel.saveToken("")
            showLoading(false)
        }

        builder.setNegativeButton(getString(R.string.tidak)) { _, _ ->
            mConfirmDialog.cancel()
        }
        builder.show()
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.pbLoading.visibility = if (isShowLoading) View.VISIBLE else View.GONE
    }

}