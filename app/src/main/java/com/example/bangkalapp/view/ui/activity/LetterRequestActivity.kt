package com.example.bangkalapp.view.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.bangkalapp.R
import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.local.MySharedPrefrence
import com.example.bangkalapp.data.network.HttpHandler
import com.example.bangkalapp.databinding.ActivityLetterRequestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class LetterRequestActivity : AppCompatActivity() {
    var letter_type_id = 0
    lateinit var binding: ActivityLetterRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLetterRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        letter_type_id = intent.getIntExtra("id", 0)
        showDataSpinner()

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            binding.btn.setOnClickListener {
                if (binding.etNik.text.toString() == "" ||
                    binding.etAddress.text.toString() == "" ||
                    binding.spinnerGender.selectedItem.toString() == "" ||
                    binding.etPlace.text.toString() == "" ||
                    binding.etCity.text.toString() == "" ||
                    binding.etReiligion.text.toString() == "" ||
                    binding.etFatherName.text.toString() == "" ||
                    binding.etMotherName.text.toString() == "") {

                    Helper.showErrorToast(this@LetterRequestActivity, "Semua input harus diisi")
                    return@setOnClickListener
                }

                sendData()
            }

        }
    }

    fun sendData() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pb.visibility = View.VISIBLE
                    binding.btn.visibility = View.GONE
                }

                val json = JSONObject().apply {
                    put("letter_type_id", letter_type_id)
                    put("nik", binding.etNik.text.toString())
                    put("address", binding.etAddress.text.toString())
                    put("gender", binding.spinnerGender.selectedItem)
                    put("place_of_birth", binding.etPlace.text.toString())
                    put("citizenship", binding.etCity.text.toString())
                    put("religion", binding.etReiligion.text.toString())
                    put("father_name", binding.etFatherName.text.toString())
                    put("mother_name", binding.etMotherName.text.toString())
                }

                val response = HttpHandler().request(
                    "letterRequest",
                    "POST",
                    MySharedPrefrence.getToken(this@LetterRequestActivity),
                    json.toString()
                )

                if (response.code in 200..300) {
                    withContext(Dispatchers.Main) {
                        Helper.showSuccessToast(
                            this@LetterRequestActivity,
                            "Berhasil mengirim permintaan surat"
                        )
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Helper.showErrorToast(
                            this@LetterRequestActivity,
                            "Gagal mengirim permintaan surat"
                        )
                        withContext(Dispatchers.Main) {
                            binding.pb.visibility = View.GONE
                            binding.btn.visibility = View.VISIBLE
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Helper.showErrorToast(this@LetterRequestActivity, e.message!!)
            binding.pb.visibility = View.GONE
            binding.btn.visibility = View.VISIBLE

        }
    }


    fun showDataSpinner() {
        val list: MutableList<String> = mutableListOf()
        list.add("Laki-Laki")
        list.add("Perempuan")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            list
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = adapter
    }
}