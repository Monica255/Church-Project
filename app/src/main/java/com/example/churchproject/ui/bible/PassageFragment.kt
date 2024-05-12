package com.example.churchproject.ui.bible

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.churchproject.R
import com.example.churchproject.databinding.FragmentLoginBinding
import com.example.churchproject.databinding.FragmentPassageBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

@AndroidEntryPoint
class PassageFragment : Fragment() {
    private var _binding: FragmentPassageBinding? = null
    private val binding get() = _binding!!
    private val viewModel:BibleViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireActivity())
        this.binding.rvPassage.layoutManager=layoutManager

        val adapter=PassageAdapter{
            viewModel.selectedPassage.value=it
        }
        this.binding.rvPassage.adapter=adapter

        viewModel.passages.observe(requireActivity()){
            adapter.list = it
            adapter.notifyDataSetChanged()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPassageBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}