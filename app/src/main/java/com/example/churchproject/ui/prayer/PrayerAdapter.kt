package com.example.churchproject.ui.prayer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.churchproject.R
import com.example.churchproject.core.data.source.remote.model.Attendance
import com.example.churchproject.core.data.source.remote.model.Prayer
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ItemAttendanceBinding
import com.example.churchproject.databinding.ItemListPrayerBinding

class PrayerAdapter(
    private val role: String,
    private val onDelete: (String) -> Unit,
    private val onCheck: (String,Boolean) -> Unit
) : RecyclerView.Adapter<PrayerAdapter.ItemViewHolder>() {
    lateinit var context: Context
    var list = listOf<Prayer>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemListPrayerBinding = ItemListPrayerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        context = parent.context
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrayerAdapter.ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class ItemViewHolder(private val binding: ItemListPrayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(prayer: Prayer) {
            binding.cbDone.isChecked = if(prayer.status==0)false else true
            binding.tvPrayer.text = prayer.prayer
            binding.tvTime.text = Helper.formatDateAttendance(prayer.timestamp)

            if (role == "admin") {
                binding.llStatus.visibility=View.GONE
                binding.llDelete.visibility = View.VISIBLE
                binding.llEmail.visibility = View.VISIBLE
                binding.tvEmail.text = prayer.user_email
                binding.btnDelete.setOnClickListener {
                    onDelete.invoke(prayer.id.toString())
                }

                binding.cbDone.setOnCheckedChangeListener { buttonView, isChecked ->
                    onCheck.invoke(prayer.id.toString(),isChecked)
                }


            } else {
                binding.llStatus.visibility=View.VISIBLE
                binding.llDelete.visibility = View.GONE
                binding.llEmail.visibility = View.GONE
                binding.tvStatus.text = if(prayer.status==1) "Selesai" else "Sedang diproses"
                binding.tvStatus.setTextColor(if(prayer.status==1) context.getColor(R.color.green) else context.getColor(R.color.maroon))
            }

        }
    }
}