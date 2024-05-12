package com.example.churchproject.ui.bible

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.churchproject.core.data.source.remote.model.Passage
import com.example.churchproject.databinding.ItemPassageBinding

class PassageAdapter (private val onClick: (Passage)->Unit): RecyclerView.Adapter<PassageAdapter.ItemViewHolder>() {

    var list= listOf<Passage>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemPassageBinding = ItemPassageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PassageAdapter.ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size

    inner class ItemViewHolder(private val binding: ItemPassageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(passage: Passage) {
            binding.tvPassage.text = passage.name
            binding.root.setOnClickListener {
                onClick.invoke(passage)
            }
        }
    }
}
