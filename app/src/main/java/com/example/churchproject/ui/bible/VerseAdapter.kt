package com.example.churchproject.ui.bible

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.churchproject.core.data.source.remote.model.Passage
import com.example.churchproject.databinding.ItemPassageBinding
import com.example.churchproject.databinding.ItemVerseBinding


class VerseAdapter (private val onClick: (Int)->Unit): RecyclerView.Adapter<VerseAdapter.ItemViewHolder>() {

    var list= listOf<Int>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemVerseBinding = ItemVerseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerseAdapter.ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size

    inner class ItemViewHolder(private val binding: ItemVerseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(verse:Int) {
            binding.tvVerse.text = verse.toString()
            binding.root.setOnClickListener {
                onClick.invoke(verse)
            }
        }
    }
}