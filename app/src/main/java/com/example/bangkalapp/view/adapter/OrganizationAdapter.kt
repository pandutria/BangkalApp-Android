package com.example.bangkalapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkalapp.data.model.Organization
import com.example.bangkalapp.databinding.ItemOrganizationBinding

class OrganizationAdapter(private val list: List<Organization>): RecyclerView.Adapter<OrganizationAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemOrganizationBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemOrganizationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val organization = list[position]
            tvTitle.text = organization.title
            tvDesc.text = organization.description

            Glide.with(holder.itemView.context)
                .load(organization.image_url)
                .into(imgImage)
        }
    }
}