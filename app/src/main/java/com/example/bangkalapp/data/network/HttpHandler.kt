package com.example.bangkalapp.data.network

import android.media.session.MediaSession.Token
import com.example.bangkalapp.data.model.HttpModel
import java.net.HttpURLConnection
import java.net.URL

class HttpHandler {
    val baseUrl = "http://127.0.0.1:8000/api/"

    fun request(
        endpoint: String,
        method: String? = "GET",
        token: String? = null,
        rBody: String? = null
    ): HttpModel {
        val url = URL(baseUrl + endpoint)
        val conn = url.openConnection() as HttpURLConnection
        conn.setRequestProperty("Content-Type", "application/json")
        conn.requestMethod = method

        if (token != null) {
            conn.setRequestProperty("Authorization", "Bearer ${token}")
        }

        if (rBody != null) {
            conn.doOutput = true
            conn.outputStream.use { it.write(rBody.toByteArray()) }
        }

        val code = conn.responseCode
        val body = try {
            conn.inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            conn.errorStream.bufferedReader().use { it.readText() }
        }

        return HttpModel(code, body)
    }
}