package com.thymleaf.music.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.thymleaf.music.R
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentHomeBinding
import com.thymleaf.music.uamp.media.BROWSER_STORAGE
import com.thymleaf.music.uamp.utils.InjectorUtils
import com.thymleaf.music.uamp.viewmodels.MainActivityViewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class HomeFragment : BaseSimpleFragment()  {

    private lateinit var binding: FragmentHomeBinding

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel> {
        InjectorUtils.provideMainActivityViewModel(requireContext())
    }

    override fun setViewBinding(): ViewBinding {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        binding.tvPlayRecord.setOnClickListener{
            mainActivityViewModel.showFragment(RecentFragment.newInstance(null))
        }
        binding.tvFavoriteSong.setOnClickListener{ }
        binding.tvDiskSong.setOnClickListener{
            startDiskPageWithPermissionCheck()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_MEDIA_LOCATION)
    fun startDiskPage()
    {
        val arg = Bundle().apply { putString(ROOT_ID, BROWSER_STORAGE) }

        mainActivityViewModel.showFragment(MusicAlbumFragment.newInstance(arg), tag= TAG)
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleForCamera(request: PermissionRequest) {
        showRationaleDialog( request)
    }

    private fun showRationaleDialog(request: PermissionRequest) {
        AlertDialog.Builder(requireContext())
                .setPositiveButton(R.string.button_allow) { _, _ -> request.proceed() }
                .setNegativeButton(R.string.button_deny) { _, _ -> request.cancel() }
                .setCancelable(false)
                .setMessage(R.string.permission_storage_rationale)
                .show()
    }


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
                HomeFragment().apply {
                    arguments = bundle
                }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}

private const val TAG = "HomeFragment"