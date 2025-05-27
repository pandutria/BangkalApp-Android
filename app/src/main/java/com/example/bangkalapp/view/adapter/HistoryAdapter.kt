package com.example.bangkalapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.model.History
import com.example.bangkalapp.databinding.ItemHistoryBinding

class HistoryAdapter(private val list: List<History>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val history = list[position]
            tvTitle.text = history.title
            tvText.text = history.text
            tvDate.text = "Tanggal : " + Helper.convertDate(history.date!!)

            Glide.with(holder.itemView.context)
                .load(history.image_url)
                .into(imgImage)
        }
    }
}