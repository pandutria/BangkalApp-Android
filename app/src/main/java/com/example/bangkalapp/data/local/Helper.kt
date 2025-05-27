package com.example.bangkalapp.data.local

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale

object Helper {
    fun log(string: String) {
        Log.e("DataApi", "Error : $string")
    }

    fun convertDate(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(input)
        return outputFormat.format(date!!)
    }
}