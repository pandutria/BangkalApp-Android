package com.example.bangkalapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkalapp.data.model.News
import com.example.bangkalapp.databinding.ItemNewsBinding

class NewsAdapter(private val list: List<News>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val news = list[position]
            tvTitle.text = news.text
            tvText.text = news.text

            Glide.with(holder.itemView.context)
                .load(news.image_url)
                .into(imgImage)
        }
    }
}