package com.example.churchproject.ui.event

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.churchproject.R
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.Event
import com.example.churchproject.core.data.source.remote.model.RequestAttendance
import com.example.churchproject.core.util.EXTRA_DATE
import com.example.churchproject.core.util.EXTRA_END_TIME
import com.example.churchproject.core.util.EXTRA_ID_EVENT
import com.example.churchproject.core.util.EXTRA_NAME_EVENT
import com.example.churchproject.core.util.EXTRA_START_TIME
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ActivityEventBinding
import com.example.churchproject.ui.bible.PassageAdapter
import com.example.churchproject.ui.event.add.AddEventActivity
import com.example.churchproject.ui.jadwal.JadwalActivity
import com.example.churchproject.ui.loginsignup.LoginSignupViewModel
import com.example.churchproject.ui.qr.AttendanceActivity
import com.example.churchproject.ui.qr.AttendanceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {
    lateinit var binding: ActivityEventBinding
    private val viewModel: EventViewModel by viewModels()
    lateinit var adapter: EventAdapter
    private val loginViewModel: LoginSignupViewModel by viewModels()
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                getEvents()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        this.binding.rvEvent.layoutManager = layoutManager

        getToken()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.fabAdd.setOnClickListener {
            activityResultLauncher.launch(Intent(this, AddEventActivity::class.java))
        }
    }

    private fun getToken() {
        loginViewModel.getToken().observe(this) {
            val role = Helper.getRole(it)
            getEvents()

            binding.fabAdd.visibility = if (role == "admin") View.VISIBLE else View.GONE

            val ondelete :(String)->Unit = {
                showConfirmDialog(it)
            }
            adapter = EventAdapter(role,ondelete){
                val intent = Intent(this@EventActivity,AddEventActivity::class.java)
                intent.putExtra(EXTRA_NAME_EVENT,it.nama_kegiatan)
                intent.putExtra(EXTRA_ID_EVENT,it.id_kegiatan)
                intent.putExtra(EXTRA_START_TIME,it.jam_mulai)
                intent.putExtra(EXTRA_END_TIME,it.jam_berakhir)
                intent.putExtra(EXTRA_DATE,it.tanggal)
                startActivity(intent)
            }

            binding.rvEvent.adapter = adapter
        }
    }

    private fun getEvents() {
        viewModel.getAllEvent().observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    it.data?.let {
                        showData(it)
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun showData(list:List<Event>){
        if(list.isNotEmpty()){
            binding.tvNoData.visibility=View.GONE
            binding.rvEvent.visibility=View.VISIBLE
            adapter.list=list
            adapter.notifyDataSetChanged()
        }else{
            binding.tvNoData.visibility=View.VISIBLE
            binding.rvEvent.visibility=View.GONE
        }
    }
    private fun showConfirmDialog(it: String) {
        val builder = AlertDialog.Builder(this)
        val mConfirmDialog = builder.create()
        builder.setTitle(getString(R.string.hapus_data))
        builder.setMessage(getString(R.string.yakin_ingin_hapus))
        builder.create()

        builder.setPositiveButton(getString(R.string.ya)) { _, _ ->
            viewModel.deleteEvent(it).observe(this) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }

                    is Resource.Success -> {
                        showLoading(false)
                        it.data?.let {
                            if (it.status == "success") {
                                getEvents()
                            }
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Error -> {
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