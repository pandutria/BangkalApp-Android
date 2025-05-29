package com.example.bangkalapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.model.Announcement
import com.example.bangkalapp.databinding.ItemAnnouncementBinding

class AnnouncementAdapter(private val list: List<Announcement>): RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemAnnouncementBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemAnnouncementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val announcement = list[position]
            tvTitle.text = announcement.title
            tvText.text = announcement.text
            tvDate.text = Helper.convertDate(announcement.date!!)

            Glide.with(holder.itemView.context)
                .load(announcement.image_url)
                .into(imgImage)
        }
    }
}