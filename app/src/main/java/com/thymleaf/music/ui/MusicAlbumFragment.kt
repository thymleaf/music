package com.thymleaf.music.ui

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thymleaf.music.R
import com.thymleaf.music.adapter.MediaItemAdapter
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentMusicAlbumBinding
import com.thymleaf.music.uamp.utils.InjectorUtils
import com.thymleaf.music.uamp.viewmodels.MainActivityViewModel
import com.thymleaf.music.uamp.viewmodels.MediaItemFragmentViewModel
import com.thymleaf.music.util.RecyclerViewUtil.setItemDividerDuration
import kotlin.math.abs

private const val ALBUM_ID_KEY = "album_id_key"
const val ROOT_ID = "ROOT_ID"

class MusicAlbumFragment : BaseSimpleFragment() {

    private lateinit var binding: FragmentMusicAlbumBinding

    private lateinit var container: ViewGroup

    private lateinit var adapter: MediaItemAdapter

    private lateinit var mediaId: String


    private val mediaItemFragmentViewModel by viewModels<MediaItemFragmentViewModel> {
        InjectorUtils.provideMediaItemFragmentViewModel(requireContext(), mediaId, arguments)
    }

    private val viewModel by viewModels<MainActivityViewModel> {
        InjectorUtils.provideMainActivityViewModel(requireContext())
    }


    override fun setViewBinding(): ViewBinding {
        binding = FragmentMusicAlbumBinding.inflate(layoutInflater)

        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        mediaId = arguments?.getString(ROOT_ID, "/") ?: "/"
        container = binding.container
        val toolbar: Toolbar = binding.toolbar
        val appBarLayout: AppBarLayout = binding.appBarLayout
        val fab: FloatingActionButton = binding.fab
        val albumImage = binding.albumImage
        val albumTitle = binding.albumTitle
        val albumArtist = binding.albumArtist
        val songRecyclerView = binding.songRecyclerView
        val musicPlayerContainer = binding.musicPlayerContainer
        val albumId = arguments?.getLong(ALBUM_ID_KEY, 0L)


        appBarLayout.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { appBarLayout1: AppBarLayout, verticalOffset: Int ->
                    val verticalOffsetPercentage = abs(verticalOffset).toFloat() / appBarLayout1.totalScrollRange.toFloat()
                    if (verticalOffsetPercentage > 0.2f && fab.isOrWillBeShown) {
                        fab.hide()
                    } else if (verticalOffsetPercentage <= 0.2f && fab.isOrWillBeHidden
                            && musicPlayerContainer.visibility != View.VISIBLE) {
                        fab.show()
                    }
                })


        // Set up album info area
        albumImage.setImageResource(R.drawable.album_ellen_qin_unsplash)
        albumTitle.setText("本地歌曲")
        albumArtist.setText("本地歌曲")


        songRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        setItemDividerDuration(requireContext(), songRecyclerView, R.drawable.inset_recycler_divider)
        adapter = MediaItemAdapter(R.layout.item_media)
        adapter.setOnItemClickListener{ adapter, _, position ->
            run {
                viewModel.playMediaId((adapter.getItem(position) as MediaBrowserCompat.MediaItem).mediaId!!)
            }
        }
        songRecyclerView.adapter = adapter

        fab.setOnClickListener {
//            TransitionManager.beginDelayedTransition(container, musicPlayerEnterTransform)
            fab.visibility = View.GONE
            musicPlayerContainer.visibility = View.VISIBLE
        }

        musicPlayerContainer.setOnClickListener {
//            TransitionManager.beginDelayedTransition(container, musicPlayerExitTransform)
            musicPlayerContainer.visibility = View.GONE
            fab.visibility = View.VISIBLE
        }

        mediaItemFragmentViewModel.mediaItems.observe(viewLifecycleOwner,
                { list ->
                    adapter.setNewInstance(list)
                })

    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): MusicAlbumFragment =
                MusicAlbumFragment().apply { arguments = bundle }
    }

}