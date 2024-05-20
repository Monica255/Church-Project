package com.example.churchproject.ui.prayer

import android.os.Bundle
import android.util.Log
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
import com.example.churchproject.databinding.ActivityPrayerBinding
import com.example.churchproject.databinding.ActivityPrayerListBinding
import com.example.churchproject.ui.loginsignup.LoginSignupViewModel
import com.example.churchproject.ui.qr.AttendanceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrayerListActivity : AppCompatActivity() {
    lateinit var binding: ActivityPrayerListBinding
    lateinit var adapter: PrayerAdapter
    private val loginViewModel: LoginSignupViewModel by viewModels()
    private val viewModel:PrayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrayerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        val layoutManager = LinearLayoutManager(this)
        this.binding.rvPrayers.layoutManager = layoutManager

        adapter = PrayerAdapter {
            showConfirmDialog(it)
        }
        binding.rvPrayers.adapter = adapter

        getAllData()
    }
    private fun showConfirmDialog(it:String) {
        val builder = AlertDialog.Builder(this)
        val mConfirmDialog = builder.create()
        builder.setTitle(getString(R.string.hapus_data))
        builder.setMessage(getString(R.string.yakin_ingin_hapus))
        builder.create()

        builder.setPositiveButton(getString(R.string.ya)) { _, _ ->
            viewModel.deletePrayer(it).observe(this){
                when(it){
                    is Resource.Loading->{
                        showLoading(true)
                    }
                    is Resource.Success->{
                        showLoading(false)
                        it.data?.let {
                            if(it.status=="success"){
                                getAllData()
                            }
//                            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Resource.Error->{
                        showLoading(true)
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
    private fun getAllData(){
        viewModel.getAllPrayer().observe(this){
            when(it){
                is Resource.Loading->{
                    showLoading(true)
                }
                is Resource.Success->{
                    showLoading(false)
                    it.data?.let {
                        Log.d("prayerrr",it.toString())
                        if(it.status=="success"){
                            adapter.list=it.data
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                is Resource.Error->{
                    showLoading(false)
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.pbLoading.visibility = if (isShowLoading) View.VISIBLE else View.GONE
    }
}