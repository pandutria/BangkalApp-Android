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
import com.example.bangkalapp.data.network.HttpHandler
import com.example.bangkalapp.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tvLogin.setOnClickListener {
                finish()
            }

            btn.setOnClickListener {
                if (etFullname.text.toString() == "" || etUsername.text.toString() == ""
                    || etPassword.text.toString() == "") {

                    Helper.showErrorToast(this@RegisterActivity,"Semua input harus di isi")
                    return@setOnClickListener
                }
                register()
            }
        }
    }

    fun register() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pb.visibility = View.VISIBLE
                    binding.btn.visibility = View.GONE
                }

                val json = JSONObject().apply {
                    put("username", binding.etUsername.text.toString())
                    put("fullname", binding.etFullname.text.toString())
                    put("password", binding.etPassword.text.toString())
                    put("role", "user")
                }

                val response = HttpHandler().request("register", "POST", null, json.toString())

                if (response.code in 200..300) {
                    withContext(Dispatchers.Main) {
                        Helper.showSuccessToast(this@RegisterActivity, "Daftar akun berhasil!!")
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Helper.showErrorToast(this@RegisterActivity, "Daftar akun Gagal!!")
                        binding.pb.visibility = View.GONE
                        binding.btn.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
            Helper.showErrorToast(this@RegisterActivity, "Eror : ${e.message}")
            binding.pb.visibility = View.GONE
            binding.btn.visibility = View.VISIBLE
        }
    }
}