package com.example.churchproject.ui.event

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.churchproject.R
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ActivityEventBinding
import com.example.churchproject.ui.bible.PassageAdapter
import com.example.churchproject.ui.loginsignup.LoginSignupViewModel
import com.example.churchproject.ui.qr.AttendanceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {
    lateinit var binding:ActivityEventBinding
    private val viewModel:EventViewModel by viewModels()
    lateinit var adapter: EventAdapter
    private val loginViewModel:LoginSignupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        this.binding.rvEvent.layoutManager=layoutManager


        loginViewModel.getToken().observe(this) {
            val role = Helper.getRole(it)
            getEvents()
            adapter = EventAdapter (role) {
                showConfirmDialog(it)
            }
            binding.rvEvent.adapter = adapter
        }


        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getEvents(){
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
    }

    private fun showConfirmDialog(it:String) {
        val builder = AlertDialog.Builder(this)
        val mConfirmDialog = builder.create()
        builder.setTitle(getString(R.string.hapus_data))
        builder.setMessage(getString(R.string.yakin_ingin_hapus))
        builder.create()

        builder.setPositiveButton(getString(R.string.ya)) { _, _ ->
            viewModel.deleteEvent(it).observe(this){
                when(it){
                    is Resource.Loading->{
                        showLoading(true)
                    }
                    is Resource.Success->{
                        showLoading(false)
                        it.data?.let {
                            if(it.status=="success"){
                                getEvents()
                            }
                            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Resource.Error->{
                        showLoading(false)
                    }
                }
            }
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