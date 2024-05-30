package com.example.churchproject.ui.event

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.churchproject.core.data.source.remote.model.Event
import com.example.churchproject.core.data.source.remote.model.Passage
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ItemEventBinding
import com.example.churchproject.databinding.ItemPassageBinding

class EventAdapter(
    private val role: String,
    private val onDelete: (String) -> Unit,
    private val onEdit: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.ItemViewHolder>() {

    var list = listOf<Event>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemEventBinding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventAdapter.ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class ItemViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            val date = Helper.getDate(event.tanggal)
            binding.tvEvent.text = event.nama_kegiatan
            binding.tvTime.text = event.jam_mulai + " - " + event.jam_berakhir
            binding.tvDate.text = date.date
            binding.tvId.text = "ID Kegiatan: ${event.id_kegiatan}"
            binding.tvMonth.text = date.month
            binding.btnDelete.setOnClickListener {
                onDelete.invoke(event.id_kegiatan.toString())
            }

            binding.btnEdit.setOnClickListener {
                Log.d("editevent",event.toString())
                onEdit.invoke(event)
            }

            if (role == "admin") {
                binding.llDelete.visibility = View.VISIBLE
                binding.llIdEvent.visibility = View.VISIBLE
            } else {
                binding.llDelete.visibility = View.GONE
                binding.llIdEvent.visibility = View.GONE
            }
        }
    }
}
