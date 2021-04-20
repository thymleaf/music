package com.thymleaf.music.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thymleaf.music.R

class MineFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) =
                MineFragment().apply {
                    arguments = bundle
                }
    }
}