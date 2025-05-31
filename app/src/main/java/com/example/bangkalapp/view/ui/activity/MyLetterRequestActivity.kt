package com.example.bangkalapp.view.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.bangkalapp.R
import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.local.MySharedPrefrence
import com.example.bangkalapp.data.model.LetterRequest
import com.example.bangkalapp.data.model.LetterType
import com.example.bangkalapp.data.network.HttpHandler
import com.example.bangkalapp.databinding.ActivityMyLetterRequestBinding
import com.example.bangkalapp.view.adapter.LetterRequestAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class MyLetterRequestActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyLetterRequestBinding

    // Permission request launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (allGranted) {
            // All permissions granted, proceed with your logic
            showData()
        } else {
            // Some permissions denied, handle accordingly
            Helper.showErrorToast(this, "Storage permissions are required for this feature")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMyLetterRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            checkAndRequestPermissions()
        }
    }

    private fun checkAndRequestPermissions() {
        val readPermission = Manifest.permission.READ_EXTERNAL_STORAGE
        val writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE

        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, readPermission) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(readPermission)
        }

        if (ContextCompat.checkSelfPermission(this, writePermission) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(writePermission)
        }

        if (permissionsToRequest.isNotEmpty()) {
            // Request the permissions
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            // All permissions already granted
            showData()
        }
    }

    fun showData() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pbRv.visibility = View.VISIBLE
                    binding.rv.visibility = View.GONE
                }

                val response = HttpHandler().request("letterRequest/byUser", "GET", MySharedPrefrence.getToken(this@MyLetterRequestActivity))
                val list = mutableListOf<LetterRequest>()

                if (response.code in 200..300) {
                    val jsonArray = JSONArray(response.body)

                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        val letterType = data.getJSONObject("letter_type")
                        list.add(LetterRequest(
                            id  = data.getInt("id"),
                            status = data.getString("status"),
                            letter_type = LetterType(
                                id = letterType.getInt("id"),
                                name = letterType.getString("name"),
                                description = letterType.getString("description")
                            )
                        ))
                    }
                }

                withContext(Dispatchers.Main) {
                    binding.rv.adapter = LetterRequestAdapter(list)
                    binding.pbRv.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
        }
    }
}