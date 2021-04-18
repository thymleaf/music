package com.thymleaf.music.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomappbar.BottomAppBar
import com.thymleaf.music.R
import com.thymleaf.music.base.BaseSimpleActivity
import com.thymleaf.music.databinding.ActivityBottomAppBinding

class BottomAppActivity : BaseSimpleActivity() {

    private lateinit var binding: ActivityBottomAppBinding
    private lateinit var bottomAppBar: BottomAppBar


    override fun setBindingView(): View {
        binding = ActivityBottomAppBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        bottomAppBar = binding.bottomAppBar;

        bottomAppBar.setNavigationOnClickListener {

        }

        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.next ->{
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}