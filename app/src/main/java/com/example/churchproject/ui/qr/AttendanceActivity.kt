package com.example.churchproject.ui.qr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.churchproject.R
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.Event
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ActivityAttendanceBinding
import com.example.churchproject.ui.loginsignup.LoginSignupActivity
import com.example.churchproject.ui.loginsignup.LoginSignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttendanceActivity : AppCompatActivity() {
    lateinit var binding: ActivityAttendanceBinding
    lateinit var adapter: AttendanceAdapter
    private val viewModel: AttendanceViewModel by viewModels()
    private val loginViewModel: LoginSignupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        this.binding.rvAttendance.layoutManager = layoutManager

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        loginViewModel.getToken().observe(this) {
            val role = Helper.getRole(it)
            var arg = ""
            if (role == "admin") arg = "all" else arg = Helper.getEmail(it)
            getAttendance(arg)
            adapter = AttendanceAdapter(role) {
                showConfirmDialog(it,arg)
            }
            binding.rvAttendance.adapter = adapter


        }

    }

    private fun getAttendance(arg:String){
        viewModel.getAttendance(arg).observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    it.data?.let {
                        adapter.list = it
                        adapter.notifyDataSetChanged()
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showConfirmDialog(it:String,arg: String) {
        val builder = AlertDialog.Builder(this)
        val mConfirmDialog = builder.create()
        builder.setTitle(getString(R.string.hapus_data))
        builder.setMessage(getString(R.string.yakin_ingin_hapus))
        builder.create()

        builder.setPositiveButton(getString(R.string.ya)) { _, _ ->
            viewModel.deleteAttendance(it).observe(this){
                when(it){
                    is Resource.Loading->{
                        showLoading(true)
                    }
                    is Resource.Success->{
                        it.data?.let {
                            if(it.status=="success"){
                                getAttendance(arg)

                            }
                            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                        }
                        showLoading(false)
                    }
                    is Resource.Error->{
                        showLoading(false)
                        Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
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