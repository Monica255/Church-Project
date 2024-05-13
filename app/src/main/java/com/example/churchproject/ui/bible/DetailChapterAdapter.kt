package com.example.churchproject.ui.bible

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.churchproject.core.data.source.remote.model.Verse
import com.example.churchproject.databinding.ItemVersesBinding

class DetailChapterAdapter : RecyclerView.Adapter<DetailChapterAdapter.ItemViewHolder>() {

    var list= listOf<Verse>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemVersesBinding = ItemVersesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailChapterAdapter.ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size

    inner class ItemViewHolder(private val binding: ItemVersesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(verse: Verse) {
            if(verse.type=="title"){
                binding.tvVerses.text = verse.content
                binding.tvVerses.typeface = Typeface.DEFAULT_BOLD
            }else{
                binding.tvVerses.typeface = Typeface.DEFAULT
                binding.tvVerses.text = "${verse.verse}. ${verse.content}"
            }

        }
    }
}
