package com.example.bangkalapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkalapp.data.model.Potential
import com.example.bangkalapp.databinding.ItemPotentialBinding

class PotentialAdapter(private val list: List<Potential>): RecyclerView.Adapter<PotentialAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemPotentialBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemPotentialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val potential = list[position]
            tvTitle.text = potential.title
            tvDesc.text = potential.description

            Glide.with(holder.itemView.context)
                .load(potential.image_url)
                .into(imgImage)
        }
    }
}