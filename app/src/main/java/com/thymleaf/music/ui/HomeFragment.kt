package com.thymleaf.music.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.thymleaf.music.R
import com.thymleaf.music.base.BaseFragment
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentHomeBinding

class HomeFragment : BaseSimpleFragment()  {

    private lateinit var binding: FragmentHomeBinding

    override fun setViewBinding(): ViewBinding {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {

        binding.tvDistSong.setOnClickListener { }
        binding.tvPlayRecord.setOnClickListener { }
        binding.tvFavoriteSong.setOnClickListener { }

    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) =
                HomeFragment().apply {
                    arguments = bundle
                }
    }
}