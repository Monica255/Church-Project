package com.example.churchproject.ui.document

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.churchproject.R
import com.example.churchproject.core.data.source.remote.model.DocReq
import com.example.churchproject.core.data.source.remote.model.Prayer
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ItemDocReqBinding
import com.example.churchproject.databinding.ItemListPrayerBinding

class DocReqAdapter(
    private val role: String,
    private val onDelete: (String) -> Unit,
    private val onCheck: (String, Boolean) -> Unit
) : RecyclerView.Adapter<DocReqAdapter.ItemViewHolder>() {

    var list = listOf<DocReq>()
    lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemDocReqBinding = ItemDocReqBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        context = parent.context
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DocReqAdapter.ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class ItemViewHolder(private val binding: ItemDocReqBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(doc: DocReq) {
            binding.cbDone.isChecked = if (doc.status == 0) false else true
            binding.tvDocType.text = doc.doc_type
            binding.tvTime.text = doc.max_date

            if (role == "admin") {
                binding.llStatus.visibility = View.GONE
                binding.llDelete.visibility = View.VISIBLE
                binding.llEmail.visibility = View.VISIBLE
                binding.tvEmail.text = doc.user_email
                binding.btnDelete.setOnClickListener {
                    onDelete.invoke(doc.id.toString())
                }

                binding.cbDone.setOnCheckedChangeListener { buttonView, isChecked ->
                    onCheck.invoke(doc.id.toString(),isChecked)
                }
            } else {
                binding.llStatus.visibility = View.VISIBLE
                binding.llDelete.visibility = View.GONE
                binding.llEmail.visibility = View.GONE
                binding.tvStatus.text = if (doc.status == 1) "Selesai" else "Sedang diproses"
                binding.tvStatus.setTextColor(
                    if (doc.status == 1) context.getColor(R.color.green) else context.getColor(
                        R.color.maroon
                    )
                )

            }

        }
    }
}