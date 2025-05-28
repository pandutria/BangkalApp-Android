package com.example.bangkalapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkalapp.data.model.Village
import com.example.bangkalapp.databinding.ItemVillageOfficialsBinding

class VillageAdapter(private val list: List<Village>): RecyclerView.Adapter<VillageAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemVillageOfficialsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemVillageOfficialsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val village = list[position]
            tvName.text = village.name
            tvOrganization.text = village.organization!!.title
            tvContact.text = village.contact

            Glide.with(holder.itemView.context)
                .load(village.image_url)
                .into(imgImage)
        }
    }
}