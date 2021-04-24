package com.thymleaf.music.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thymleaf.music.R
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentMusicAlbumBinding
import kotlin.math.abs

private const val ALBUM_ID_KEY = "album_id_key"

class MusicAlbumFragment : BaseSimpleFragment() {

    private lateinit var binding: FragmentMusicAlbumBinding

    private lateinit var container: ViewGroup

    override fun setViewBinding(): ViewBinding {
        binding = FragmentMusicAlbumBinding.inflate(layoutInflater)

        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {
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
    }

    companion object {
        @JvmStatic
        fun newInstance(albumId: Long) =
                MusicAlbumFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ALBUM_ID_KEY, albumId)
                    }


                }
    }


}