package com.example.bangkalapp.data.local

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.bangkalapp.data.model.LetterRequest
import com.example.bangkalapp.databinding.PdfLayoutBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfGenerator(private val context: Context, private val letter: LetterRequest) {

    fun generatePdf() {
        // Inflate the layout
        val binding = PdfLayoutBinding.inflate(LayoutInflater.from(context))

        // Isi data ke layout
        binding.tvFullname.text = letter.user?.fullname ?: "-"
        binding.tvNik.text = letter.nik ?: "-"
        binding.tvAlamat.text = letter.address ?: "-"
        binding.tvGender.text = letter.gender ?: "-"
        binding.tvPlace.text = letter.place_of_birth ?: "-"
        binding.tvCitizenship.text = letter.citizenship ?: "-"
        binding.tvReligion.text = letter.religion ?: "-"
        binding.tvFatherName.text = letter.father_name ?: "-"
        binding.tvMotherName.text = letter.mother_name ?: "-" // ini kamu isi pakai `nik`, seharusnya `mother_name`
        binding.tvLeterType.text = letter.letter_type?.name ?: "-"


        val displayMetrics = context.resources.displayMetrics
        val width = (595 * displayMetrics.density).toInt() // Convert points to pixels
        val height = (842 * displayMetrics.density).toInt() // Convert points to pixels

        binding.root.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )
        binding.root.layout(0, 0, width, height)

        // Buat dokumen PDF
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(width, height, 1).create()
        val page = document.startPage(pageInfo)

        // Gambar layout ke canvas
        binding.root.draw(page.canvas)

        document.finishPage(page)

        // Simpan file ke penyimpanan internal
        val fileName = "Surat_${System.currentTimeMillis()}.pdf"
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, fileName)

        try {
            val outputStream = FileOutputStream(file)
            document.writeTo(outputStream)
            outputStream.close()
            Toast.makeText(
                context,
                "PDF berhasil disimpan di ${file.absolutePath}",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Gagal menyimpan PDF: ${e.message}", Toast.LENGTH_LONG).show()
        }

        document.close()
    }
}

//
