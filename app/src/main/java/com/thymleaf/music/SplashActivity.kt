package com.thymleaf.music

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thymleaf.music.base.BaseSimpleActivity

class SplashActivity : BaseSimpleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun setContentLayout(savedInstanceState: Bundle?): Int {
        TODO("Not yet implemented")
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}