package com.example.bangkalapp.view.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkalapp.R
import com.example.bangkalapp.data.local.PdfGenerator
import com.example.bangkalapp.data.model.LetterRequest
import com.example.bangkalapp.databinding.ItemLetterRequestBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class LetterRequestAdapter(private val list: List<LetterRequest>) :
    RecyclerView.Adapter<LetterRequestAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemLetterRequestBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemLetterRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val letterRequest = list[position]
            tvName.text = letterRequest.letter_type!!.name
            tvDesc.text = letterRequest.letter_type.description

            Glide.with(holder.itemView.context)
                .load(letterRequest.letter_type.image_url)
                .into(imgImage)

            if (letterRequest.status == "pending") {
                tvStatus.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.bkg_pending)
                tvStatus.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.primary
                    )
                )
                tvStatus.text = "Pending"
                card.visibility = View.GONE
            }

            if (letterRequest.status == "approved") {
                tvStatus.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.bkg_approved)
                tvStatus.setTextColor(Color.parseColor("#5CB338"))
                tvStatus.text = "Disetujui"
                card.visibility = View.VISIBLE
            }

            if (letterRequest.status == "rejected") {
                tvStatus.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.bkg_rejected)
                tvStatus.setTextColor(Color.parseColor("#CB0404"))
                tvStatus.text = "Ditolak"
                card.visibility = View.GONE
            }

            btn.setOnClickListener {
                val context = holder.itemView.context
                PdfGenerator(holder.itemView.context, letterRequest).generatePdf()
                Toast.makeText(context, "PDF sedang dibuat...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}