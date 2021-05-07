package com.thymleaf.music.ui

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentAlbumListBinding

class AlbumListFragment : BaseSimpleFragment() {

    private lateinit var binding: FragmentAlbumListBinding


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
                AlbumListFragment().apply {
                    arguments = Bundle().apply {
                        arguments = bundle
                    }
                }
    }

    override fun setViewBinding(): ViewBinding {
        binding = FragmentAlbumListBinding.inflate(layoutInflater)
        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {

    }
}