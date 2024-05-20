package com.example.churchproject.ui.prayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.churchproject.core.data.source.remote.model.Attendance
import com.example.churchproject.core.data.source.remote.model.Prayer
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ItemAttendanceBinding
import com.example.churchproject.databinding.ItemListPrayerBinding

class PrayerAdapter (private val onDelete: (String)->Unit): RecyclerView.Adapter<PrayerAdapter.ItemViewHolder>() {

    var list= listOf<Prayer>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemListPrayerBinding = ItemListPrayerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrayerAdapter.ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size

    inner class ItemViewHolder(private val binding: ItemListPrayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(prayer: Prayer) {
            binding.tvEmail.text=prayer.user_email
            binding.tvPrayer.text =prayer.prayer
            binding.tvTime.text = Helper.formatDateAttendance(prayer.timestamp)
            binding.btnDelete.setOnClickListener {
                onDelete.invoke(prayer.id.toString())
            }
        }
    }
}