package com.example.bangkalapp.view.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.bangkalapp.R
import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.local.MySharedPrefrence
import com.example.bangkalapp.data.model.LetterType
import com.example.bangkalapp.data.network.HttpHandler
import com.example.bangkalapp.databinding.FragmentServiceBinding
import com.example.bangkalapp.view.adapter.LetterTypeAdapter
import com.example.bangkalapp.view.ui.activity.LoginActivity
import com.example.bangkalapp.view.ui.activity.MainActivity
import com.example.bangkalapp.view.ui.activity.MyLetterRequestActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.log

class ServiceFragment : Fragment() {
    lateinit var binding: FragmentServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentServiceBinding.inflate(layoutInflater, container, false)

        binding.apply {
            imgLogout.setOnClickListener {
                logout()
            }

            imgMyLetter.setOnClickListener {
                startActivity(Intent(requireContext(), MyLetterRequestActivity::class.java))
            }

            me()
            showData()
        }

        return binding.root
    }

    fun logout() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                val response = HttpHandler().request("logout", "POST", MySharedPrefrence.getToken(requireContext()))

                if (response.code in 200..300) {
                    val context = requireContext()
                    if (context is MainActivity) {
                        MySharedPrefrence.deleteToken(context)
                        context.startActivity(Intent(context, LoginActivity::class.java))
                        context.finish()
                    }
                }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
        }
    }

    fun me() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                val response = HttpHandler().request("me", "GET", MySharedPrefrence.getToken(requireContext()))

                if (response.code in 200..300) {
                    val user = JSONObject(response.body)

                    withContext(Dispatchers.Main) {
                        binding.tvName.text = "${user.getString("fullname")}!"
                    }
                }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
        }
    }

    fun showData() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pbRv.visibility = View.VISIBLE
                    binding.rv.visibility = View.GONE
                }

                val response = HttpHandler().request("letterType")
                val list = mutableListOf<LetterType>()

                if (response.code in 200..300) {
                    val jsonArray = JSONArray(response.body)

                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)

                        list.add(
                            LetterType(
                                id = data.getInt("id"),
                                name = data.getString("name"),
                                description = data.getString("description"),
                                image_url = data.getString("image_url")
                            )
                        )
                    }
                }
                withContext(Dispatchers.Main) {
                    binding.rv.adapter = LetterTypeAdapter(list)
                    binding.pbRv.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
        }
    }


}