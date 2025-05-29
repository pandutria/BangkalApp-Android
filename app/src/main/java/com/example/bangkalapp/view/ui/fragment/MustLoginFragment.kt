package com.example.bangkalapp.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bangkalapp.R
import com.example.bangkalapp.databinding.FragmentMustLoginBinding

class MustLoginFragment : Fragment() {
   lateinit var binding: FragmentMustLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMustLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }




}