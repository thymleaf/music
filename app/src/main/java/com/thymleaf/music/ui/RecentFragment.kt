package com.thymleaf.music.ui

import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.thymleaf.music.R
import com.thymleaf.music.adapter.MediaItemAdapter
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentRecentBinding
import com.thymleaf.music.uamp.utils.InjectorUtils
import com.thymleaf.music.util.RecyclerViewUtil
import com.thymleaf.music.viewmodel.MediaDataViewModel

class RecentFragment: BaseSimpleFragment() {

    private lateinit var binding: FragmentRecentBinding

    private lateinit var recyclerView: RecyclerView


//    private val serviceJob = SupervisorJob()
//    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private val mediaDataViewModel by viewModels<MediaDataViewModel> {
        InjectorUtils.providerMediaDataViewModel(requireContext())
    }

    override fun setViewBinding(): ViewBinding {
        binding = FragmentRecentBinding.inflate(layoutInflater)

        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        RecyclerViewUtil.setItemDividerDuration(requireContext(), recyclerView)
        val adapter = MediaItemAdapter(R.layout.item_media_containter)
        recyclerView.adapter = adapter

        mediaDataViewModel.getRecent().observe(viewLifecycleOwner, {

            adapter.setList(it.map { mediaData ->
                val descriptionCompat =  MediaDescriptionCompat.Builder().setTitle(mediaData.title)
                        .setSubtitle(mediaData.artist)
                        .setMediaId(mediaData.id)
                        .setMediaUri(Uri.parse(mediaData.playUri))
                        .build()

                MediaBrowserCompat.MediaItem(descriptionCompat, FLAG_PLAYABLE)
            })
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as MusicContainerActivity).hideToolBar(true)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?): RecentFragment =
                RecentFragment().apply { arguments = bundle }
    }

}