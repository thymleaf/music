package com.thymleaf.music.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.thymleaf.music.adapter.TabFragmentAdapter
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentMainBinding

/**
 * main fragment
 */
class MainFragment : BaseSimpleFragment() {


    private lateinit var binding: FragmentMainBinding

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: TabFragmentAdapter

    private val pageTitle = mutableListOf("歌单", "发现", "我的")


    companion object {

        @JvmStatic
        fun newInstance(bundle: Bundle?) =
                MainFragment().apply {
                    bundle?.let {
                        arguments = bundle
                    }
                }
    }

    override fun setViewBinding(): ViewBinding {

        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        val fragments = mutableListOf<Fragment>()
        fragments.add(TrackListFragment.newInstance(null))
        fragments.add(SightseeingFragment.newInstance(null))
        fragments.add(HomeFragment.newInstance(null))

        adapter = TabFragmentAdapter(activity, fragments)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pageTitle[position]
        }.attach()

    }

    override fun onResume() {
        super.onResume()
        (activity as MusicContainerActivity).hideToolBar(false)
    }
}