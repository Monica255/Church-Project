package com.example.churchproject.ui.bible

import android.os.Bundle
import android.support.annotation.StringRes
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.churchproject.R
import com.example.churchproject.core.data.Resource
import com.example.churchproject.core.data.source.remote.model.Passage
import com.example.churchproject.databinding.ActivityBibleBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BibleActivity : AppCompatActivity() {
    lateinit var binding:ActivityBibleBinding
    private val viewModel:BibleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBibleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sectionsPagerAdapter = SectionsPagerAdapter(this,viewModel)
        binding.viewPager.adapter = sectionsPagerAdapter


        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        binding.toolbar.setOnClickListener{
           viewModel.selectedPassage.value= Passage(1,"kej","Kejadian",120)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.selectedPassage.observe(this){
            if(it!=null){
                binding.viewPager.currentItem = 1
            }
        }

        viewModel.getPassage().observe(this){
            when(it){
                is Resource.Success->{
                    showLoading(false)
                    it.data?.let{
                        viewModel.passages.value=it.data
                    }
                }
                is Resource.Loading->{
                    showLoading(true)
                }
                is Resource.Error->{
                    showLoading(false)
                }
            }

            Log.d("bible","activity "+it.data?.data.toString())
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.kitab,
            R.string.pasal
        )
    }
    private fun showLoading(isShowLoading: Boolean) {
        binding.pbLoading.visibility = if (isShowLoading) View.VISIBLE else View.GONE
    }
}