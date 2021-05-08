package com.thymleaf.music.ui

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.AppBarLayout
import com.thymleaf.music.R
import com.thymleaf.music.adapter.MediaItemAdapter
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentMusicAlbumBinding
import com.thymleaf.music.model.StorageSource
import com.thymleaf.music.uamp.media.extensions.flag
import com.thymleaf.music.uamp.media.library.BROWSER_STORAGE
import com.thymleaf.music.uamp.media.library.MusicSource
import com.thymleaf.music.uamp.utils.InjectorUtils
import com.thymleaf.music.uamp.viewmodels.*
import com.thymleaf.music.util.RecyclerViewUtil.setItemDividerDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.math.abs

const val ROOT_ID = "ROOT_ID"

class MusicAlbumFragment : BaseSimpleFragment() {

    private lateinit var binding: FragmentMusicAlbumBinding

    private lateinit var container: ViewGroup

    private lateinit var adapter: MediaItemAdapter

    private lateinit var mediaId: String

    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var mediaSource: MusicSource

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private val mediaItemFragmentViewModel by viewModels<MediaItemFragmentViewModel> {
        InjectorUtils.provideMediaItemFragmentViewModel(requireContext(), mediaId, arguments)
    }

    private val viewModel by viewModels<MainActivityViewModel> {
        InjectorUtils.provideMainActivityViewModel(requireContext())
    }


    private val nowPlayingViewModel by viewModels<NowPlayingFragmentViewModel> {
        InjectorUtils.provideNowPlayingFragmentViewModel(requireContext())
    }


    override fun setViewBinding(): ViewBinding {
        binding = FragmentMusicAlbumBinding.inflate(layoutInflater)

        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        mediaId = arguments?.getString(ROOT_ID, "/") ?: "/"
        container = binding.container
        val appBarLayout: AppBarLayout = binding.appBarLayout
        val albumImage = binding.albumImage
        val songRecyclerView = binding.songRecyclerView

        appBarLayout.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { appBarLayout1: AppBarLayout, verticalOffset: Int ->
                    val verticalOffsetPercentage = abs(verticalOffset).toFloat() / appBarLayout1.totalScrollRange.toFloat()
                    if (verticalOffsetPercentage > 0.5f) {
                        albumImage.alpha = 0f
                    } else if (verticalOffsetPercentage <= 0.2f) {
                        albumImage.alpha = 1f
                    }
                })

        // Set up album info area
        albumImage.setImageResource(R.drawable.album_ellen_qin_unsplash)

        layoutManager = LinearLayoutManager(requireContext())
        songRecyclerView.layoutManager = layoutManager
        setItemDividerDuration(requireContext(), songRecyclerView, R.drawable.inset_recycler_divider)
        adapter = MediaItemAdapter(R.layout.item_media_containter)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.item_container -> {
                    viewModel.playMedia((adapter.getItem(position) as MediaBrowserCompat.MediaItem))
                }
            }
        }
        songRecyclerView.adapter = adapter

        nowPlayingViewModel.mediaMetadata.observe(viewLifecycleOwner, {
            adapter.data.forEach{ item ->
                val isPlaying = it.id == item.mediaId
                item.description.extras?.apply {
                    putBoolean(KEY_IS_PLAYING, isPlaying)
                    putInt(KEY_PLAY_STATE, it.playState)
                }
            }
            adapter.notifyDataSetChanged()
        })

        nowPlayingViewModel.playbackState

        mediaItemFragmentViewModel.mediaItems
        when (mediaId) {
            BROWSER_STORAGE -> {
                mediaSource = StorageSource(requireContext())
                serviceScope.launch {
                    mediaSource.load()
                }

                mediaSource.whenReady { successfullyInitialized ->
                    if (successfullyInitialized) {

                        val mediaItems = mediaSource.getMediaItems()
                                .map { MediaBrowserCompat.MediaItem(it.description, it.flag) }
                                .toMutableList()

                        adapter.setList(mediaItems)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

//    fun scrollToIndex(index: Int) {
//        layoutManager.let {
//            layoutManager.scrollToPosition(index)
//        }
//    }

    override fun onResume() {
        super.onResume()
        (activity as MusicContainerActivity).hideToolBar(true)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): MusicAlbumFragment =
                MusicAlbumFragment().apply { arguments = bundle }
    }

}