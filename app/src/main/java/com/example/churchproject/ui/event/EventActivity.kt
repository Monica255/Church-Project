package com.example.churchproject.ui.event

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.churchproject.R
import com.example.churchproject.core.data.Resource
import com.example.churchproject.databinding.ActivityEventBinding
import com.example.churchproject.ui.bible.PassageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {
    lateinit var binding:ActivityEventBinding
    private val viewModel:EventViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        this.binding.rvEvent.layoutManager=layoutManager

        val adapter= EventAdapter{

        }
        this.binding.rvEvent.adapter=adapter
        viewModel.getAllEvent().observe(this){
            when(it){
                is Resource.Loading->{
                    showLoading(true)
                }
                is Resource.Success->{
                    showLoading(false)
                    it.data?.let {
                        adapter.list =it
                        adapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error->{
                    showLoading(false)
                }
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
    private fun showLoading(isShowLoading: Boolean) {
        binding.pbLoading.visibility = if (isShowLoading) View.VISIBLE else View.GONE
    }
}