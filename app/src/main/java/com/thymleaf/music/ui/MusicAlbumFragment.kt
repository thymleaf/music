package com.thymleaf.music.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.view.LayoutInflater
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
import com.thymleaf.music.model.entity.MediaData
import com.thymleaf.music.uamp.media.extensions.flag
import com.thymleaf.music.uamp.media.library.BROWSER_STORAGE
import com.thymleaf.music.uamp.media.library.MusicSource
import com.thymleaf.music.uamp.utils.InjectorUtils
import com.thymleaf.music.uamp.viewmodels.KEY_IS_PLAYING
import com.thymleaf.music.uamp.viewmodels.KEY_PLAY_STATE
import com.thymleaf.music.uamp.viewmodels.MainActivityViewModel
import com.thymleaf.music.uamp.viewmodels.NowPlayingFragmentViewModel
import com.thymleaf.music.util.RecyclerViewUtil.setItemDividerDuration
import com.thymleaf.music.viewmodel.MediaDataViewModel
import kotlinx.coroutines.*
import java.util.*
import kotlin.math.abs

const val ROOT_ID = "ROOT_ID"

class MusicAlbumFragment : BaseSimpleFragment() {

    private lateinit var binding: FragmentMusicAlbumBinding

    private lateinit var container: ViewGroup

    private lateinit var adapter: MediaItemAdapter

    private lateinit var mediaId: String

    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var mediaSource: MusicSource

    private lateinit var mediaItems: MutableList<MediaBrowserCompat.MediaItem>

    private val mediaDataDataViewModel  by viewModels<MediaDataViewModel>{
        InjectorUtils.providerMediaDataViewModel(requireContext())
    }

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

//    private val mediaItemFragmentViewModel by viewModels<MediaItemFragmentViewModel> {
//        InjectorUtils.provideMediaItemFragmentViewModel(requireContext(), mediaId, arguments)
//    }

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

    @SuppressLint("InflateParams")
    override fun initFragment(savedInstanceState: Bundle?) {
        mediaId = arguments?.getString(ROOT_ID, "/") ?: "/"
        container = binding.container
        val appBarLayout: AppBarLayout = binding.appBarLayout
        val albumImage = binding.albumImage
        val songRecyclerView = binding.songRecyclerView

        appBarLayout.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { appBarLayout1, verticalOffset ->
                    val verticalOffsetPercentage = abs(verticalOffset).toFloat() / appBarLayout1.totalScrollRange.toFloat()
                    if (verticalOffsetPercentage < 1f) {
                        albumImage.alpha = 1f
                    } else {
                        albumImage.alpha = 0f
                    }
                })

        // Set up album info area
        albumImage.setImageResource(R.drawable.album_ellen_qin_unsplash)

        layoutManager = LinearLayoutManager(requireContext())
        songRecyclerView.layoutManager = layoutManager
        setItemDividerDuration(requireContext(), songRecyclerView, R.drawable.inset_recycler_divider)
        adapter = MediaItemAdapter(R.layout.item_media_containter)
        adapter.addFooterView(LayoutInflater.from(requireContext()).inflate(R.layout.footer_recycler_view, null, false))
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.item_container -> {
                    viewModel.playMedia(position, queue = mediaItems)

                    val mediaItems: MediaBrowserCompat.MediaItem = adapter.data[position] as MediaBrowserCompat.MediaItem
                    mediaDataDataViewModel.addRecent(MediaData(
                            UUID.randomUUID().toString(),
                            mediaItems.mediaId!!,
                            mediaItems.description.title.toString(),
                            mediaItems.description.subtitle.toString(),
                            playUri = mediaItems.description.mediaUri.toString(),
                            albumUri = mediaItems.description.iconUri.toString(),
                            duration = null,
                            isRecent = 1
                    ))
                }
            }
        }
        songRecyclerView.adapter = adapter

        nowPlayingViewModel.mediaMetadata.observe(viewLifecycleOwner, {
            adapter.data.forEach { item ->
                val isPlaying = it.id == item.mediaId
                item.description.extras?.apply {
                    putBoolean(KEY_IS_PLAYING, isPlaying)
                    putInt(KEY_PLAY_STATE, it.playState)
                }
            }
            adapter.notifyDataSetChanged()
        })


        when (mediaId) {
            BROWSER_STORAGE -> {
                mediaSource = StorageSource(requireContext())
                serviceScope.launch {
                    mediaSource.load()
                }

                mediaSource.whenReady { successfullyInitialized ->
                    if (successfullyInitialized) {

                        mediaItems = mediaSource.getMediaItems()
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

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): MusicAlbumFragment =
                MusicAlbumFragment().apply { arguments = bundle }
    }

}