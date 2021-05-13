package com.thymleaf.music.ui

import android.media.AudioManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thymleaf.music.R
import com.thymleaf.music.base.BaseSimpleActivity
import com.thymleaf.music.databinding.ActivityMusicContainerBinding
import com.thymleaf.music.uamp.utils.InjectorUtils
import com.thymleaf.music.uamp.viewmodels.MainActivityViewModel

class MusicContainerActivity : BaseSimpleActivity() {

    private lateinit var binding: ActivityMusicContainerBinding

    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var fab: FloatingActionButton
    private lateinit var toolbar: Toolbar

    private val viewModel by viewModels<MainActivityViewModel> {
        InjectorUtils.provideMainActivityViewModel(this)
    }

    override fun setBindingView(): View {
        binding = ActivityMusicContainerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setIsLightStatusBar(): Boolean {
        return true
    }

    fun hideToolBar(isHide: Boolean) {
        toolbar.visibility = if (isHide) View.GONE else View.VISIBLE
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)

        volumeControlStream = AudioManager.STREAM_MUSIC

        bottomAppBar = binding.bottomAppBar
        fab = binding.fab

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // set fab animation
        val operatingAnim: Animation = AnimationUtils
                .loadAnimation(this, R.anim.mtrl_extended_fab_state_list_animator)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        fab.startAnimation(operatingAnim)


        bottomAppBar.setNavigationOnClickListener {
            viewModel.showFragment(PlayQueueFragment.newInstance(Bundle()), tag = PLAY_LIST_FRAGMENT_TAG)
        }


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


        viewModel.rootMediaId.observe(this,
                { rootMediaId ->
                    rootMediaId?.let { navigateToMediaItem(it) }
                })

        viewModel.navigateToFragment.observe(this, {
            it?.getContentIfNotHandled()?.let { fragmentRequest ->
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                        R.id.fragment_container, fragmentRequest.fragment, fragmentRequest.tag
                )
                if (fragmentRequest.backStack) transaction.addToBackStack(null)
                transaction.commit()
            }
        })
    }

    private fun navigateToMediaItem(mediaId: String) {
        var fragment: MainFragment? = getMainFragment(mediaId)
        if (fragment == null) {
            fragment = MainFragment.newInstance(null)
            viewModel.showFragment(fragment, !isRootId(mediaId), mediaId)
        }
    }

    private fun isRootId(mediaId: String) = mediaId == viewModel.rootMediaId.value

    private fun getMainFragment(mediaId: String): MainFragment? {
        return supportFragmentManager.findFragmentByTag(mediaId) as MainFragment?
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

    override fun onBackPressed() {

        if (supportFragmentManager.fragments.isNotEmpty()) {
            supportFragmentManager.popBackStack()
            return
        }

        super.onBackPressed()
    }

}