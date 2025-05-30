package com.example.bangkalapp.view.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bangkalapp.R
import com.example.bangkalapp.databinding.FragmentMustLoginBinding
import com.example.bangkalapp.view.ui.activity.LoginActivity
import com.example.bangkalapp.view.ui.activity.MainActivity

class MustLoginFragment : Fragment() {
   lateinit var binding: FragmentMustLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMustLoginBinding.inflate(layoutInflater, container, false)

        binding.btn.setOnClickListener {
            val context = requireContext()
            if (context is MainActivity) {
                startActivity(Intent(context, LoginActivity::class.java))
                context.finish()
            }
        }

        return binding.root
    }




}