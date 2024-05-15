package com.example.churchproject.ui.bible

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.churchproject.core.data.Resource
import com.example.churchproject.databinding.ActivityDetailChapterBinding
import com.example.churchproject.ui.bible.ChapterFragment.Companion.EXTRA_PASSAGE
import com.example.churchproject.ui.bible.ChapterFragment.Companion.EXTRA_VERSE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailChapterActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailChapterBinding
    var passage : String?=null
    var verse =0
    private val viewModel: BibleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailChapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        this.binding.rvChapter.layoutManager=layoutManager

        val adapter=DetailChapterAdapter()
        binding.rvChapter.adapter=adapter

        passage=intent.getStringExtra(EXTRA_PASSAGE)
        verse=intent.getIntExtra(EXTRA_VERSE,0)
        if(passage!=null&&verse!=0){
            binding.toolbarTitle.text= "$passage $verse"
            viewModel.getChapter(passage!!,verse.toString()).observe(this){
                when(it){
                    is Resource.Success->{
                        showLoading(false)
                        it.data?.data?.verses?.let {
                            adapter.list = it
                            adapter.notifyDataSetChanged()
                        }
                    }
                    is Resource.Loading->{
                        showLoading(true)
                    }
                    is Resource.Error->{
                        showLoading(false)
                    }
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