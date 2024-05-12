package com.example.churchproject.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.churchproject.databinding.ActivitySplashBinding
import com.example.churchproject.ui.home.HomeActivity
import com.example.churchproject.ui.loginsignup.LoginSignupActivity
import com.example.churchproject.ui.loginsignup.LoginSignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    private val viewModel: LoginSignupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val currentUser = null
        viewModel.getToken().observe(this){
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    val intent: Intent = if (it!=""&&it!=null) {
                        Intent(this, HomeActivity::class.java)
                    } else {
                        Intent(this, LoginSignupActivity::class.java)
                    }
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                    Log.e("ERROR", e.message.toString())
                }
            }, DELAY_TIME)
        }

    }

    companion object {
        const val DELAY_TIME: Long = 2_000
    }
}