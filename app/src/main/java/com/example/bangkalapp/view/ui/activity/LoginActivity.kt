package com.example.bangkalapp.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.bangkalapp.R
import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.local.MySharedPrefrence
import com.example.bangkalapp.data.network.HttpHandler
import com.example.bangkalapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tvRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            btn.setOnClickListener {
                if (etUsername.text.toString() == ""
                    || etPassword.text.toString() == "") {

                    Helper.showErrorToast(this@LoginActivity, "Semua input harus di isi")
                    return@setOnClickListener
                }

                login()
            }
        }
    }

    fun login() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pb.visibility = View.VISIBLE
                    binding.btn.visibility = View.GONE
                }

                val json = JSONObject().apply {
                    put("username", binding.etUsername.text.toString())
                    put("password", binding.etPassword.text.toString())
                }

                val response = HttpHandler().request("login", "POST", null, json.toString())

                if (response.code in 200..300) {
                    val json = JSONObject(response.body)
                    val user = json.getJSONObject("user")
                    val token = json.getString("token")

                    withContext(Dispatchers.Main) {
                        MySharedPrefrence.saveToken(this@LoginActivity, token)
                        Helper.user = user
                        Helper.showSuccessToast(this@LoginActivity, "Login Berhasil!!")
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Helper.showErrorToast(this@LoginActivity, "Login Gagal!!")
                        binding.pb.visibility = View.GONE
                        binding.btn.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
            Helper.showErrorToast(this@LoginActivity, "Eror : ${e.message}")
            binding.pb.visibility = View.GONE
            binding.btn.visibility = View.VISIBLE
        }
    }
}