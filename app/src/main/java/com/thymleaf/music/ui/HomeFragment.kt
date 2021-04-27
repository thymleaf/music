package com.thymleaf.music.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.thymleaf.music.R
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentHomeBinding
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class HomeFragment : BaseSimpleFragment()  {

    private lateinit var binding: FragmentHomeBinding

    override fun setViewBinding(): ViewBinding {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun initFragment(savedInstanceState: Bundle?) {
        binding.tvPlayRecord.setOnClickListener{ }
        binding.tvFavoriteSong.setOnClickListener{ }
        binding.tvDiskSong.setOnClickListener{
            startDiskPageWithPermissionCheck()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_MEDIA_LOCATION)
    fun startDiskPage()
    {
        startTarget(MusicContainerActivity::class.java, arguments)
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleForCamera(request: PermissionRequest) {
        showRationaleDialog(R.string.permission_storage_rationale, request)
    }

    private fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        AlertDialog.Builder(requireContext())
                .setPositiveButton(R.string.button_allow) { _, _ -> request.proceed() }
                .setNegativeButton(R.string.button_deny) { _, _ -> request.cancel() }
                .setCancelable(false)
                .setMessage(messageResId)
                .show()
    }


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) =
                HomeFragment().apply {
                    arguments = bundle
                }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}