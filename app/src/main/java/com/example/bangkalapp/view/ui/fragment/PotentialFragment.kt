package com.example.bangkalapp.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.bangkalapp.R
import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.model.Potential
import com.example.bangkalapp.data.network.HttpHandler
import com.example.bangkalapp.databinding.FragmentPotentialBinding
import com.example.bangkalapp.databinding.FragmentProfileBinding
import com.example.bangkalapp.view.adapter.PotentialAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class PotentialFragment : Fragment() {
    lateinit var binding: FragmentPotentialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPotentialBinding.inflate(layoutInflater, container, false)

        showData()

        return binding.root
    }

    fun showData() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pbRv.visibility = View.VISIBLE
                    binding.rv.visibility = View.GONE
                }

                val response = HttpHandler().request("potential")
                val list = mutableListOf<Potential>()

                if (response.code in 200..300) {
                    val jsonArray = JSONArray(response.body)

                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)

                        list.add(Potential(
                            id = data.getInt("id"),
                            title = data.getString("title"),
                            description = data.getString("description"),
                            image_url = data.getString("image_url")
                        ))
                    }
                }

                withContext(Dispatchers.Main) {
                    binding.rv.adapter = PotentialAdapter(list)
                    binding.pbRv.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
        }
    }

}