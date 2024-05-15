package com.example.churchproject.ui.qr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.churchproject.core.data.source.remote.model.Attendance
import com.example.churchproject.core.data.source.remote.model.Event
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ItemAttendanceBinding
import com.example.churchproject.databinding.ItemEventBinding
import com.example.churchproject.ui.loginsignup.LoginSignupViewModel

class AttendanceAdapter (private val role:String,private val onDelete: (String)->Unit): RecyclerView.Adapter<AttendanceAdapter.ItemViewHolder>() {

    var list= listOf<Attendance>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemAttendanceBinding = ItemAttendanceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttendanceAdapter.ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size

    inner class ItemViewHolder(private val binding: ItemAttendanceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(att: Attendance) {
            if(role=="admin"){
                binding.llEmail.visibility=View.VISIBLE
                binding.tvEmail.text=att.email_user
                binding.btnDelete.visibility=View.VISIBLE
            }else{
                binding.llEmail.visibility=View.GONE
                binding.btnDelete.visibility=View.GONE
            }
            binding.tvEvent.text =att.name_event
            binding.tvTime.text = Helper.formatDateAttendance(att.timestamp)
            binding.btnDelete.setOnClickListener {
                onDelete.invoke(att.id.toString())
            }
        }
    }
}
