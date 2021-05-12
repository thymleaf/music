package com.thymleaf.music.ui

import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.thymleaf.music.R
import com.thymleaf.music.adapter.SongAdapter
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentSightseeingBinding
import com.thymleaf.music.model.entity.Song
import com.thymleaf.music.model.service.MediaApi
import com.thymleaf.music.uamp.utils.InjectorUtils
import com.thymleaf.music.uamp.viewmodels.MainActivityViewModel
import com.thymleaf.music.util.RecyclerViewUtil
import com.thymleaf.music.util.ToastUtil


// 发现
class SightseeingFragment : BaseSimpleFragment() {
    private lateinit var binding: FragmentSightseeingBinding

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: SongAdapter


    private lateinit var songList: List<Song>

    private lateinit var mediaItems: MutableList<MediaBrowserCompat.MediaItem>

    private val viewModel by viewModels<MainActivityViewModel> {
        InjectorUtils.provideMainActivityViewModel(requireContext())
    }

    override fun setViewBinding(): ViewBinding {
        binding = FragmentSightseeingBinding.inflate(layoutInflater)

        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SongAdapter(R.layout.item_media_containter)
        adapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.item_container -> {
                    val song = adapter.getItem(position)
                    val index = mediaItems.indexOf(mediaItems.find { mediaItem ->
                        song.id.toString() == mediaItem.description.mediaId
                    })
                    viewModel.playMedia(index, queue = mediaItems)
                }
            }
        }
        recyclerView.adapter = adapter
        RecyclerViewUtil.setItemDividerDuration(requireContext(), recyclerView)

        loadData()
    }


    private fun loadData() {

        loadRepository(repository.obtainRetrofitService(MediaApi::class.java).getSongs("2116")
        ) {
            adapter.setList(it)
            songList = it

            val ids = it.map { song -> song.id.toString() }.reduce { acc, s -> "$acc,$s" }
            loadRepository(repository.obtainRetrofitService(MediaApi::class.java).getPlayUrl(ids)) { detail ->
                mediaItems = detail.map { cur ->
                    val curSong = songList.find { song -> cur.id.toString() == song.id.toString() }
                    curSong?.detail = cur
                    curSong
                }.map { song ->

                    val artist = song?.ar?.map { ar -> ar.name }?.reduce { acc, s -> "$acc / $s" }
                    val des = MediaDescriptionCompat.Builder().setTitle(song?.name)
                            .setMediaId(song?.id.toString())
                            .setSubtitle(artist)
                            .setIconUri(Uri.parse(song?.al?.picUrl))
                            .setMediaUri(Uri.parse(song?.detail?.url))
                            .build()

                    MediaBrowserCompat.MediaItem(des, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
                }.toMutableList()
            }
        }
    }

    override fun showError(msg: String?) {
        msg?.let {
            ToastUtil.showShort(it)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
                SightseeingFragment().apply {
                    arguments = bundle
                }
    }
}