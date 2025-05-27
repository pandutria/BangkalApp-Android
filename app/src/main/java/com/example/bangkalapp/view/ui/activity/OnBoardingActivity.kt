package com.example.bangkalapp.view.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkalapp.R
import com.example.bangkalapp.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {
    lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btn.setOnClickListener {
                startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
                finish()
            }
        }

    }
}