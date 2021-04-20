package com.thymleaf.music.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.thymleaf.music.base.BaseSimpleActivity
import com.thymleaf.music.databinding.ActivitySplashBinding

class SplashActivity : BaseSimpleActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var handle: Handler

    override fun setBindingView(): View {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        binding.tvSplash.text = "闪屏页"
        handle = Handler(Looper.myLooper()!!)
        handle.postDelayed({ startTarget(MainActivity::class.java) }, 2000)
    }

}