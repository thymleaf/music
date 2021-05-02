package com.thymleaf.music.ui

import android.os.Bundle
import android.view.View
import com.thymleaf.music.R
import com.thymleaf.music.base.BaseSimpleActivity
import com.thymleaf.music.databinding.ActivityMusicContainerBinding

class MusicContainerActivity : BaseSimpleActivity() {

    private lateinit var binding: ActivityMusicContainerBinding

    override fun setBindingView(): View {
        binding = ActivityMusicContainerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setIsLightStatusBar(): Boolean {
        return true
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MusicAlbumFragment.newInstance(bundle))
                .commit()
    }
}