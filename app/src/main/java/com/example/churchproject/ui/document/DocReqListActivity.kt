package com.example.churchproject.ui.document

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
import com.example.churchproject.core.data.source.remote.model.DocReq
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ActivityDocReqListBinding
import com.example.churchproject.ui.loginsignup.LoginSignupViewModel
import com.example.churchproject.ui.prayer.PrayerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocReqListActivity : AppCompatActivity() {
    lateinit var binding:ActivityDocReqListBinding
    lateinit var adapter :DocReqAdapter
    private val viewModel: DocReqViewModel by viewModels()
    private val authViewModel: LoginSignupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocReqListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        val layoutManager = LinearLayoutManager(this)
        this.binding.rvDoc.layoutManager = layoutManager

        authViewModel.getToken().observe(this){
            val role = Helper.getRole(it)
            var arg = ""
            if (role == "admin") {
                arg = "all"
                binding.toolbarTitle.text= getString(R.string.daftar_permintaan_dokumen)
            } else {
                arg = Helper.getEmail(it)
                binding.toolbarTitle.text= getString(R.string.history)
            }

            adapter = DocReqAdapter (role){
                showConfirmDialog(it,arg)
            }
            binding.rvDoc.adapter = adapter

            getAllData(arg)
        }
    }
    private fun showConfirmDialog(it:String,email: String) {
        val builder = AlertDialog.Builder(this)
        val mConfirmDialog = builder.create()
        builder.setTitle(getString(R.string.hapus_data))
        builder.setMessage(getString(R.string.yakin_ingin_hapus))
        builder.create()

        builder.setPositiveButton(getString(R.string.ya)) { _, _ ->
            viewModel.deleteDocReq(it).observe(this){
                when(it){
                    is Resource.Loading->{
                        showLoading(true)
                    }
                    is Resource.Success->{
                        showLoading(false)
                        it.data?.let {
                           getAllData(email)
                        }
                    }
                    is Resource.Error->{
                        showLoading(true)
                        Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        builder.setNegativeButton(getString(R.string.tidak)) { _, _ ->
            mConfirmDialog.cancel()
        }
        builder.show()
    }
    private fun getAllData(email:String){
        viewModel.getAllDocReq(email).observe(this){
            when(it){
                is Resource.Loading->{
                    showLoading(true)
                }
                is Resource.Success->{
                    showLoading(false)
                    it.data?.let {
                        if(it.status=="success"){
                            showData(it.data)
                        }else{
                            showData(listOf())
                        }
                    }
                }
                is Resource.Error->{
                    showLoading(false)
                    Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showData(list:List<DocReq>){
        if(list.isNotEmpty()){
            binding.tvNoData.visibility=View.GONE
            binding.rvDoc.visibility=View.VISIBLE
            adapter.list=list
            adapter.notifyDataSetChanged()
        }else{
            binding.tvNoData.visibility=View.VISIBLE
            binding.rvDoc.visibility=View.GONE
        }
    }
    private fun showLoading(isShowLoading: Boolean) {
        binding.pbLoading.visibility = if (isShowLoading) View.VISIBLE else View.GONE
    }
}