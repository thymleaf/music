package com.thymleaf.music.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.thymleaf.music.R
import com.thymleaf.music.TabFragmentAdapter
import com.thymleaf.music.base.BaseSimpleActivity
import com.thymleaf.music.databinding.ActivityMainBinding


class MainActivity : BaseSimpleActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var fab: FloatingActionButton
    private lateinit var toolbar: Toolbar

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: TabFragmentAdapter

    private val pageTitle = mutableListOf<String>("我的", "发现", "歌单")


    override fun setBindingView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setIsLightStatusBar(): Boolean {
        return true
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)
        bottomAppBar = binding.bottomAppBar
        fab = binding.fab

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // set fab animation
        val operatingAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.mtrl_extended_fab_state_list_animator)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        fab.startAnimation(operatingAnim)


        bottomAppBar.setNavigationOnClickListener {

        }

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        val fragments = mutableListOf<Fragment>()
        fragments.add(HomeFragment.newInstance(Bundle()))
        fragments.add(SightseeingFragment.newInstance(Bundle()))
        fragments.add(PlayListFragment.newInstance(Bundle()))

        adapter = TabFragmentAdapter(this, fragments)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.text = pageTitle[position]
        }.attach()

        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.next -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {

            }
            R.id.more -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}