package com.example.churchproject.ui.event.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.churchproject.R
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.RequestEvent
import com.example.churchproject.core.util.EXTRA_DATE
import com.example.churchproject.core.util.EXTRA_END_TIME
import com.example.churchproject.core.util.EXTRA_ID_EVENT
import com.example.churchproject.core.util.EXTRA_NAME_EVENT
import com.example.churchproject.core.util.EXTRA_START_TIME
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ActivityAddEventBinding
import com.example.churchproject.ui.event.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddEventActivity : AppCompatActivity() {
    private val viewModel: EventViewModel by viewModels()
    lateinit var binding: ActivityAddEventBinding
    var eventDate:String?=null
    var id:Int=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID_EVENT,-1)
        if(id!=-1){
            binding.toolbarTitle.text = getString(R.string.edit_kegiatan)
            binding.btnSend.text = getString(R.string.edit_data)
            val name = intent.getStringExtra(EXTRA_NAME_EVENT)
            val start = intent.getStringExtra(EXTRA_START_TIME)
            val end = intent.getStringExtra(EXTRA_END_TIME)
            eventDate = intent.getStringExtra(EXTRA_DATE)


            binding.etName.setText(name)
            binding.etDate.setText(eventDate)
            binding.timePicker.setInitialSelectedTime(start?:"09:00")
            binding.timePicker2.setInitialSelectedTime(end?:"11:00")
        }else{
            binding.toolbarTitle.text = getString(R.string.add_event)
            binding.btnSend.text = getString(R.string.tambahkan)
        }

//        binding.timePicker.setInitialSelectedTime("09:00 Am")
//        binding.timePicker2.setInitialSelectedTime("11:00 Am")

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.etDate.setOnClickListener {
            showDatePicker()
        }


        binding.btnSend.setOnClickListener {
            val name = binding.etName.text.trim().toString()
            if(!name.isNullOrEmpty()&&eventDate!=null){
                val start =binding.timePicker.getCurrentlySelectedTime()
                val end =binding.timePicker2.getCurrentlySelectedTime()
                val data=RequestEvent(if(id<0)null else id,
                    name,eventDate!!,start,end
                )
                viewModel.addEvent(data).observe(this){
                    when(it){
                        is Resource.Loading->{
                            showLoading(true)
                        }
                        is Resource.Success->{
                            showLoading(false)
                            it.data?.let {
                                if(it.status=="success"){
                                    setResult(RESULT_OK)
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
            }else if(name.isNullOrEmpty()){
                Toast.makeText(this,
                    getString(R.string.nama_kegiatan_tidak_boleh_kosong),Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,
                    getString(R.string.tanggal_kegiatan_tidak_boleh_kosong),Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showDatePicker() {
        val locale = getResources().configuration.locale;
        Locale.setDefault(locale)
        val c = Calendar.getInstance()
        var showYear = c.get(Calendar.YEAR)
        var showMonth = c.get(Calendar.MONTH)
        var showDay = c.get(Calendar.DAY_OF_MONTH)
        Log.d("editevent",showYear.toString())
        Log.d("editevent",showMonth.toString())
        Log.d("editevent",showDay.toString())
        eventDate?.let {
            val x =it.split("-")
            showDay=x[0].toInt()
            showMonth=x[1].toInt()-1
            showYear=x[2].toInt()
        }

        val dpd = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                monthOfYear + 1

                binding.etDate.apply {
                    val newMonth = monthOfYear + 1
                    val formattedNumber = String.format("%02d", newMonth)
                    val text = "$dayOfMonth-$formattedNumber-$year"
                    setText(text)
                    eventDate=text
                    alpha = 1F
                }


            },
            showYear,
            showMonth,
            showDay
        )
        dpd.show()
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.pbLoading.visibility = if (isShowLoading) View.VISIBLE else View.GONE
    }
}