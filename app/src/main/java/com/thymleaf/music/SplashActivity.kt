package com.thymleaf.music

import android.os.Bundle
import android.view.View
import com.thymleaf.music.base.BaseSimpleActivity
import com.thymleaf.music.databinding.ActivitySplashBinding

class SplashActivity : BaseSimpleActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun setBindingView(): View {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        binding.tvSplash.text = "闪屏页"
    }
}