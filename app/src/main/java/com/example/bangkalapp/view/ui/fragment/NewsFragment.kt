package com.example.bangkalapp.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.bangkalapp.R
import com.example.bangkalapp.data.local.Helper
import com.example.bangkalapp.data.model.Announcement
import com.example.bangkalapp.data.model.News
import com.example.bangkalapp.data.network.HttpHandler
import com.example.bangkalapp.databinding.FragmentNewsBinding
import com.example.bangkalapp.view.adapter.AnnouncementAdapter
import com.example.bangkalapp.view.adapter.NewsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class NewsFragment : Fragment() {
    lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)

        showDataAnnouncement()
        showDataNews()

        return binding.root
    }

    fun showDataAnnouncement() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pbRvAnnouncement.visibility = View.VISIBLE
                    binding.rvAnnouncement.visibility = View.GONE
                }

                val response = HttpHandler().request("announcement")
                val list = mutableListOf<Announcement>()

                if (response.code in 200..300) {
                    val jsonArray = JSONArray(response.body)

                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)

                        list.add(
                            Announcement(
                                id = data.getInt("id"),
                                title = data.getString("title"),
                                text = data.getString("text"),
                                image_url = data.getString("image_url"),
                                date = data.getString("date"),
                            )
                        )
                    }
                }

                withContext(Dispatchers.Main) {
                    binding.rvAnnouncement.adapter = AnnouncementAdapter(list)
                    binding.pbRvAnnouncement.visibility = View.GONE
                    binding.rvAnnouncement.visibility = View.VISIBLE
                }
            }

        } catch (e: Exception) {
            Helper.log(e.message!!)
        }
    }

    fun showDataNews() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.pbRvNews.visibility = View.VISIBLE
                    binding.rvNews.visibility = View.GONE
                }

                val response = HttpHandler().request("news")
                val list = mutableListOf<News>()

                if (response.code in 200..300) {
                    val jsonArray = JSONArray(response.body)

                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        list.add(News(
                            id = data.getInt("id"),
                            title = data.getString("title"),
                            text =  data.getString("text"),
                            image_url =  data.getString("image_url"),
                        ))
                    }
                }

                withContext(Dispatchers.Main) {
                    binding.rvNews.adapter = NewsAdapter(list)
                    binding.pbRvNews.visibility = View.GONE
                    binding.rvNews.visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            Helper.log(e.message!!)
        }
    }

}