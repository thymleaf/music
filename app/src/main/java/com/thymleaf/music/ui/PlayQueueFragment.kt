package com.thymleaf.music.ui

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.thymleaf.music.R
import com.thymleaf.music.adapter.MediaItemAdapter
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentPlayQueueBinding
import com.thymleaf.music.uamp.common.MusicServiceConnection
import com.thymleaf.music.uamp.utils.InjectorUtils
import com.thymleaf.music.uamp.viewmodels.MainActivityViewModel
import com.thymleaf.music.uamp.viewmodels.NowPlayingFragmentViewModel

class PlayQueueFragment : BaseSimpleFragment() {

    private lateinit var binding: FragmentPlayQueueBinding
    private lateinit var  connection: MusicServiceConnection
    private lateinit var recyclerView: RecyclerView


    private val mainActivityViewModel by activityViewModels<MainActivityViewModel> {
        InjectorUtils.provideMainActivityViewModel(requireContext())
    }
    private val nowPlayingViewModel by viewModels<NowPlayingFragmentViewModel> {
        InjectorUtils.provideNowPlayingFragmentViewModel(requireContext())
    }


    override fun setViewBinding(): ViewBinding {
        binding = FragmentPlayQueueBinding.inflate(layoutInflater)
        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {
//        val toolbar: Toolbar = binding.toolbar
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        connection = InjectorUtils.provideMusicServiceConnection(requireContext())
        connection.transportControls.setCaptioningEnabled(true)

        recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MediaItemAdapter(R.layout.item_media_containter)
        recyclerView.adapter = adapter
        val queue = connection.mediaController.queue?.toMutableList()?.map {
            MediaBrowserCompat.MediaItem(it.description, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
        }?.toMutableList()

        queue?.let {
            adapter.setList(queue)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
                PlayQueueFragment().apply {
                    arguments = bundle
                }
    }

    override fun onResume() {
        super.onResume()
        (activity as MusicContainerActivity).hideToolBar(true)
    }
}

const val PLAY_LIST_FRAGMENT_TAG = "play_list_fragment_tag"