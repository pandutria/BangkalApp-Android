package com.example.bangkalapp.data.local

import android.content.Context
import android.content.Intent
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.bangkalapp.data.model.LetterRequest
import com.example.bangkalapp.databinding.PdfLayoutBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfGenerator(private val context: Context, private val letter: LetterRequest) {

    fun generatePdf() {
        val binding = PdfLayoutBinding.inflate(LayoutInflater.from(context))

        binding.tvFullname.text = letter.user?.fullname ?: "-"
        binding.tvNik.text = letter.nik ?: "-"
        binding.tvAlamat.text = letter.address ?: "-"
        binding.tvGender.text = letter.gender ?: "-"
        binding.tvPlace.text = letter.place_of_birth ?: "-"
        binding.tvCitizenship.text = letter.citizenship ?: "-"
        binding.tvReligion.text = letter.religion ?: "-"
        binding.tvFatherName.text = letter.father_name ?: "-"
        binding.tvMotherName.text = letter.mother_name ?: "-"
        binding.tvLeterType.text = letter.letter_type?.name ?: "-"

        val displayMetrics = context.resources.displayMetrics
        val width = (595 * displayMetrics.density).toInt()
        val height = (842 * displayMetrics.density).toInt()

        binding.root.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )

        binding.root.layout(0, 0, width, height)

        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(width, height, 1).create()
        val page = document.startPage(pageInfo)

        binding.root.draw(page.canvas)

        document.finishPage(page)

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

            AlertDialog.Builder(context)
                .setTitle("PDF Berhasil Disimpan")
                .setMessage("File telah disimpan di ${file.absolutePath}")
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton("Buka File") { _, _ ->
                    openPdfFile(file)
                }
                .setCancelable(false)
                .show()

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Gagal menyimpan PDF: ${e.message}", Toast.LENGTH_LONG).show()
        }

        document.close()
    }

    private fun openPdfFile(file: File) {
        try {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Buka PDF dengan"))
        } catch (e: Exception) {
            Toast.makeText(context, "Tidak ada aplikasi untuk membuka PDF: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}