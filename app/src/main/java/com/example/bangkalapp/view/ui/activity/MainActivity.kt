package com.example.bangkalapp.view.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.bangkalapp.R
import com.example.bangkalapp.databinding.ActivityMainBinding
import com.example.bangkalapp.view.ui.fragment.GovermentFragment
import com.example.bangkalapp.view.ui.fragment.LoadingFragment
import com.example.bangkalapp.view.ui.fragment.NewsFragment
import com.example.bangkalapp.view.ui.fragment.PotentialFragment
import com.example.bangkalapp.view.ui.fragment.ProfileFragment
import com.example.bangkalapp.view.ui.fragment.ServiceFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            showFragment(ProfileFragment())

            bottomNav.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menuProfile -> {
                        showFragment(ProfileFragment())
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.menuGovernment -> {
                        showGovermentFragmentWithLoading()
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.menuNews -> {
                        showFragment(NewsFragment())
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.menuPotential -> {
                        showFragment(PotentialFragment())
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.menuService -> {
                        showFragment(ServiceFragment())
                        return@setOnNavigationItemSelectedListener true
                    }

                    else -> false
                }
            }
        }

    }

    fun showGovermentFragmentWithLoading() {
        showFragment(LoadingFragment())

        val fragment = GovermentFragment()
        fragment.listener = object : GovermentFragment.OnGovermentLoaded {
            override fun onLoaded() {
                showFragment(fragment)
            }
        }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, fragment)
            .commit()
    }
}