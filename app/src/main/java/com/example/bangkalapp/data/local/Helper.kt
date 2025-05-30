package com.example.bangkalapp.data.local

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.example.bangkalapp.R
import io.github.muddz.styleabletoast.StyleableToast
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

object Helper {
    var user = JSONObject()

    fun log(string: String) {
        Log.e("DataApi", "Error : $string")
    }

    fun convertDate(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(input)
        return outputFormat.format(date!!)
    }

    fun showSuccessToast(context: Context, message: String) {
        StyleableToast.Builder(context)
            .text(message)
            .textColor(Color.WHITE)
            .backgroundColor(Color.parseColor("#5CB338"))
            .solidBackground()
            .cornerRadius(5)
            .textBold()
            .textSize(14f)
            .font(R.font.poppins_medium)
            .show()
    }

    fun showErrorToast(context: Context, message: String) {
        StyleableToast.Builder(context)
            .text(message)
            .textColor(Color.WHITE)
            .backgroundColor(Color.parseColor("#CB0404"))
            .solidBackground()
            .cornerRadius(5)
            .textBold()
            .textSize(14f)
            .font(R.font.poppins_medium)
            .show()
    }
}