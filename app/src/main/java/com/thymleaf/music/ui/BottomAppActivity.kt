package com.thymleaf.music.ui

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thymleaf.music.R
import com.thymleaf.music.base.BaseSimpleActivity
import com.thymleaf.music.databinding.ActivityBottomAppBinding


class BottomAppActivity : BaseSimpleActivity() {

    private lateinit var binding: ActivityBottomAppBinding
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var fab: FloatingActionButton


    override fun setBindingView(): View {
        binding = ActivityBottomAppBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        bottomAppBar = binding.bottomAppBar
        fab = binding.fab
        val operatingAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.mtrl_extended_fab_state_list_animator)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        fab.startAnimation(operatingAnim)


        val decor: View = window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

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