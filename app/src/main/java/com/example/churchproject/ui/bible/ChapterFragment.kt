package com.example.churchproject.ui.bible

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.FragmentChapterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChapterFragment : Fragment() {

    private var _binding: FragmentChapterBinding? = null
    private val binding get() = _binding!!

    private val viewModel:BibleViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireActivity(),5)
        this.binding.rvVerse.layoutManager=layoutManager

        val adapter=ChapterAdapter{
            val intent = Intent(requireActivity(), DetailChapterActivity::class.java)
            intent.putExtra(EXTRA_PASSAGE,viewModel.selectedPassage.value?.abbr)
            intent.putExtra(EXTRA_VERSE,it)
            startActivity(intent)
        }
        binding.rvVerse.adapter=adapter

        viewModel.selectedPassage.observe(requireActivity()){
            if(it!=null){
                adapter.list = Helper.generateNumberList(it.chapter)
                adapter.notifyDataSetChanged()
                binding.tvPassageName.visibility=View.VISIBLE
                binding.tvPassageName.text=it.name
            }else{
                binding.tvPassageName.visibility=View.GONE
                binding.tvPassageName.text=""
            }
        }


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapterBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    companion object{
        const val EXTRA_PASSAGE = "passage"
        const val EXTRA_VERSE ="verse"
    }
}