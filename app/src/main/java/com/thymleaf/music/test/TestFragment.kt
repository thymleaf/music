package com.thymleaf.music.test

import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.library.common.di.component.AppComponent
import com.thymleaf.music.base.BaseFragment
import com.thymleaf.music.di.contract.DiskSongContract
import com.thymleaf.music.di.presenter.DiskSongPresenter

class TestFragment : BaseFragment<DiskSongPresenter?>(), DiskSongContract.View {

    override fun setupFragmentComponent(appComponent: AppComponent?) {

    }

    override fun showLoading() {}
    override fun showContent() {}
    override fun showNoNetwork() {}
    override fun showError(msg: String) {}
    override fun getHostContext(): Context? {
        return null
    }

    override fun setViewBinding(): ViewBinding? {
        return null
    }

    override fun initFragment(savedInstanceState: Bundle) {}
}