package com.example.bangkalapp.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.bangkalapp.R
import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.model.History
import com.example.bangkalapp.data.network.HttpHandler
import com.example.bangkalapp.databinding.FragmentProfileBinding
import com.example.bangkalapp.view.adapter.HistoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        showData()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        showData()
    }

    fun showData() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pbRv.visibility = View.VISIBLE
                }

                val response = HttpHandler().request("history")
                val list = mutableListOf<History>()

                if (response.code in 200..300) {
                    val jsonArray = JSONArray(response.body)

                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        list.add(
                            History(
                                id = data.getInt("id"),
                                title = data.getString("title"),
                                text = data.getString("text"),
                                image_url = data.getString("image_url"),
                                date = data.getString("date")
                            )
                        )
                    }

                    withContext(Dispatchers.Main) {
                        binding.rv.adapter = HistoryAdapter(list)
                    }
                }
                withContext(Dispatchers.Main) {
                    binding.pbRv.visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
        }
    }

}