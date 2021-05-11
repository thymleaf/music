package com.thymleaf.music.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentFavoriteBinding
import com.thymleaf.music.viewmodel.MediaDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class FavoriteFragment : BaseSimpleFragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private val mediaDataViewModel: MediaDataViewModel by viewModels()


    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)


    override fun setViewBinding(): ViewBinding {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {

        serviceScope.launch {
            mediaDataViewModel.getFavorite().observe(viewLifecycleOwner, Observer {

            })
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() =
                FavoriteFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}