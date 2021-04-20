package com.thymleaf.music.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thymleaf.music.R


// 发现
class SightseeingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sightseeing, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) =
                SightseeingFragment().apply {
                    arguments = bundle
                }
    }
}