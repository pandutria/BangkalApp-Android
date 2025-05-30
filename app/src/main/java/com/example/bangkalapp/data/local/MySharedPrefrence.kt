package com.example.bangkalapp.data.local

import android.content.Context

object MySharedPrefrence {
    val token = "token"
    val sharedKey = "sharedKey"

    fun saveToken(context: Context, tokenUser: String) {
        val shared = context.getSharedPreferences(sharedKey, Context.MODE_PRIVATE)
        with(shared.edit()) {
            putString(token, tokenUser)
            apply()
        }
    }

    fun getToken(context: Context): String? {
        val shared = context.getSharedPreferences(sharedKey, Context.MODE_PRIVATE)
        return shared.getString(token, null)
    }

    fun deleteToken(context: Context) {
        val shared = context.getSharedPreferences(sharedKey, Context.MODE_PRIVATE)
        with(shared.edit()) {
            remove(token)
            apply()
        }
    }

}