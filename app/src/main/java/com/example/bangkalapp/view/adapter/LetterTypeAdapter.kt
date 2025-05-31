package com.example.bangkalapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkalapp.data.model.LetterType
import com.example.bangkalapp.databinding.ItemLetterTypeBinding
import com.example.bangkalapp.view.ui.activity.LetterRequestActivity

class LetterTypeAdapter(private val list: List<LetterType>): RecyclerView.Adapter<LetterTypeAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemLetterTypeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemLetterTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val letterType = list[position]
            tvTitle.text = letterType.name
            tvText.text = letterType.description

            Glide.with(holder.itemView.context)
                .load(letterType.image_url)
                .into(imgImage)

            btn.setOnClickListener {
                holder.itemView.context.startActivity(Intent(holder.itemView.context, LetterRequestActivity::class.java).apply {
                    putExtra("id", letterType.id)
                })
            }
        }
    }
}