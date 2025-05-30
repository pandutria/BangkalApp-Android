package com.example.bangkalapp.data.network

import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.model.HttpModel
import java.net.HttpURLConnection
import java.net.URL

class HttpHandler {
    val baseUrl = "http://192.168.13.69:8000/api/"

    fun request(
        endpoint: String,
        method: String? = "GET",
        token: String? = null,
        rBody: String? = null
    ): HttpModel {
        var code: Int? = null
        var body: String? = null

        try {
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

            code = conn.responseCode
            body = try {
                conn.inputStream.bufferedReader().use { it.readText() }
            } catch (e: Exception) {
                conn.errorStream.bufferedReader().use { it.readText() }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
        }

        return HttpModel(code!!, body!!)
    }
}