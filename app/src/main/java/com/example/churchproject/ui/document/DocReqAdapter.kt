package com.example.churchproject.ui.document

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.churchproject.core.data.source.remote.model.DocReq
import com.example.churchproject.core.data.source.remote.model.Prayer
import com.example.churchproject.core.util.Helper
import com.example.churchproject.databinding.ItemDocReqBinding
import com.example.churchproject.databinding.ItemListPrayerBinding

class DocReqAdapter (private val role:String,private val onDelete: (String)->Unit): RecyclerView.Adapter<DocReqAdapter.ItemViewHolder>() {

    var list= listOf<DocReq>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding: ItemDocReqBinding = ItemDocReqBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DocReqAdapter.ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size

    inner class ItemViewHolder(private val binding: ItemDocReqBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(doc: DocReq) {

            binding.tvDocType.text =doc.doc_type
            binding.tvTime.text = doc.max_date

            if(role=="admin"){
                binding.btnDelete.visibility= View.VISIBLE
                binding.llEmail.visibility= View.VISIBLE
                binding.tvEmail.text=doc.user_email
                binding.btnDelete.setOnClickListener {
                    onDelete.invoke(doc.id.toString())
                }
            }else{
                binding.btnDelete.visibility= View.GONE
                binding.llEmail.visibility= View.GONE
            }

        }
    }
}