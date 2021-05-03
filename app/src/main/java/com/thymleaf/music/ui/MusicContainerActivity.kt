package com.thymleaf.music.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.thymleaf.music.R
import com.thymleaf.music.base.BaseSimpleActivity
import com.thymleaf.music.databinding.ActivityMusicContainerBinding
import com.thymleaf.music.uamp.utils.InjectorUtils
import com.thymleaf.music.uamp.viewmodels.MainActivityViewModel

class MusicContainerActivity : BaseSimpleActivity() {

    private lateinit var binding: ActivityMusicContainerBinding

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

    override fun initActivity(savedInstanceState: Bundle?) {
        super.initActivity(savedInstanceState)

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

    override fun onBackPressed() {

        if (supportFragmentManager.fragments.isNotEmpty())
        {
            supportFragmentManager.popBackStack()
            return
        }

        super.onBackPressed()
    }

}