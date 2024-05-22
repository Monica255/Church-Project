package com.example.churchproject.ui.document

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.churchproject.R
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.RequestDoc
import com.example.churchproject.core.data.source.remote.model.RequestPrayer
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ActivityDocReqBinding
import com.example.churchproject.ui.loginsignup.LoginSignupViewModel
import com.example.churchproject.ui.prayer.PrayerListActivity
import com.example.churchproject.ui.prayer.PrayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class DocReqActivity : AppCompatActivity() {
    lateinit var binding: ActivityDocReqBinding
    private var date: String? = null
    private var letter: String? = null
    private val viewModel: DocReqViewModel by viewModels()
    private val authViewModel: LoginSignupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocReqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.rgLetters.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<RadioButton>(checkedId)
            if (rb.text == getString(R.string.surat_babtis)) {
                letter = getString(R.string.surat_babtis)
            } else if (rb.text == getString(R.string.surat_pernikahan)) {
                letter = getString(R.string.surat_pernikahan)
            } else if (rb.text == getString(R.string.surat_keterangan_kematian)) {
                letter = getString(R.string.surat_keterangan_kematian)
            } else if (rb.text == getString(R.string.surat_keterangan_anggota_jemaat)) {
                letter = getString(R.string.surat_keterangan_anggota_jemaat)
            } else if (rb.text == getString(R.string.surat_ijin_penggunaan_fasilitas_gereja)) {
                letter = getString(R.string.surat_ijin_penggunaan_fasilitas_gereja)
            } else {
                letter = null
            }
        }
        binding.etDate.setOnClickListener {
            showDatePicker()
        }
        binding.btnSend.setOnClickListener {

            if(!date.isNullOrEmpty()&&!letter.isNullOrEmpty()){
                sendData(date!!,letter!!)
            }else{
                Toast.makeText(this,"Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
        binding.fabHistory.setOnClickListener {
            startActivity(Intent(this, DocReqListActivity::class.java))
        }

    }
    private fun showDatePicker() {
        val locale = getResources().configuration.locale;
        Locale.setDefault(locale)
        val c = Calendar.getInstance()
        var showYear = c.get(Calendar.YEAR)
        var showMonth = c.get(Calendar.MONTH)
        var showDay = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                monthOfYear + 1

                binding.etDate.apply {
                    val newMonth = monthOfYear + 1
                    val formattedNumber = String.format("%02d", newMonth)
                    val text = "$dayOfMonth-$formattedNumber-$year"
                    setText(text)
                    date=text
                    alpha = 1F
                }


            },
            showYear,
            showMonth,
            showDay
        )
        dpd.show()
    }

    private fun sendData(date:String,letter:String){
        authViewModel.getToken().observe(this){
            if(!it.isNullOrEmpty()){
                val email = Helper.getEmail(it)
                viewModel.addDocReq(RequestDoc(email,date,letter)).observe(this){
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
