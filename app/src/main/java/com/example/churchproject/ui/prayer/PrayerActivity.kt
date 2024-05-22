package com.example.churchproject.ui.prayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.churchproject.R
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.RequestPrayer
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ActivityPrayerBinding
import com.example.churchproject.ui.loginsignup.LoginSignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityPrayerBinding
    private val viewModel:PrayerViewModel by viewModels()
    private val authViewModel:LoginSignupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSend.setOnClickListener {
            val prayer = binding.etPrayer.text?.trim().toString()
            if(!prayer.isNullOrEmpty()){
                sendData(prayer)
            }else{
                Toast.makeText(this,"Teks tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabHistory.setOnClickListener {
            startActivity(Intent(this,PrayerListActivity::class.java))
        }

    }

    private fun sendData(prayer:String){
        authViewModel.getToken().observe(this){
            if(!it.isNullOrEmpty()){
                val email = Helper.getEmail(it)
                viewModel.addPrayer(RequestPrayer(email,prayer)).observe(this){
                    when(it){
                        is Resource.Loading->{
                            showLoading(true)
                        }
                        is Resource.Success->{
                            showLoading(false)
                            it.data?.let {
                                if(it.status=="success"){
                                    finish()
                                }
                                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()

                            }
                        }
                        is Resource.Error->{
                            showLoading(false)
                            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.pbLoading.visibility = if (isShowLoading) View.VISIBLE else View.GONE
    }
}