package com.example.churchproject.ui.bible

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.churchproject.R
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.FragmentPassageBinding
import com.example.churchproject.databinding.FragmentVerseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerseFragment : Fragment() {

    private var _binding: FragmentVerseBinding? = null
    private val binding get() = _binding!!

    private val viewModel:BibleViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireActivity(),5)
        this.binding.rvVerse.layoutManager=layoutManager

        val adapter=VerseAdapter{

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
        _binding = FragmentVerseBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}