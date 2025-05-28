package com.example.bangkalapp.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.bangkalapp.R
import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.model.Organization
import com.example.bangkalapp.data.model.Village
import com.example.bangkalapp.data.network.HttpHandler
import com.example.bangkalapp.databinding.FragmentGovermentBinding
import com.example.bangkalapp.view.adapter.OrganizationAdapter
import com.example.bangkalapp.view.adapter.VillageAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class GovermentFragment : Fragment() {
    lateinit var binding: FragmentGovermentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGovermentBinding.inflate(layoutInflater, container, false)

        showData()
        showDataVillage()

        return binding.root
    }


    fun showData() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pbRv.visibility = View.VISIBLE
                }

                val response = HttpHandler().request("organization")
                val list = mutableListOf<Organization>()

                if (response.code in 200..300) {
                    val jsonArray = JSONArray(response.body)

                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        list.add(
                            Organization(
                                id = data.getInt("id"),
                                title = data.getString("title"),
                                description = data.getString("description"),
                                level = data.getInt("level"),
                                image_url = data.getString("image_url")
                            )
                        )
                    }
                    withContext(Dispatchers.Main) {
                        binding.rv.adapter = OrganizationAdapter(list)
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

    fun showDataVillage() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pbRv2.visibility = View.VISIBLE
                }

                val response = HttpHandler().request("village")
                val list = mutableListOf<Village>()

                if (response.code in 200..300) {
                    val jsonArray = JSONArray(response.body)

                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        val dataOrganization = data.getJSONObject("organization")
                        list.add(
                            Village(
                                id = data.getInt("id"),
                                name = data.getString("name"),
                                contact = data.getString("contact"),
                                image_url = data.getString("image_url"),
                                organization = Organization(
                                    title = dataOrganization.getString("title")
                                )
                            )
                        )
                    }

                    withContext(Dispatchers.Main) {
                        binding.rv2.adapter = VillageAdapter(list)
                    }
                }

                withContext(Dispatchers.Main) {
                    binding.pbRv2.visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
        }
    }
}