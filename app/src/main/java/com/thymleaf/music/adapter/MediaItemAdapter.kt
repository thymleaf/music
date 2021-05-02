package com.thymleaf.music.adapter

import android.support.v4.media.MediaBrowserCompat
import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.thymleaf.music.R

class MediaItemAdapter(@LayoutRes private val layoutResId: Int) : BaseQuickAdapter<MediaBrowserCompat.MediaItem, BaseViewHolder>(layoutResId)
{
    override fun convert(holder: BaseViewHolder, item: MediaBrowserCompat.MediaItem) {
        holder.setText(R.id.tv_title, item.description.title)
                .setText(R.id.tv_subtitle, item.description.subtitle)
    }

}


//class MediaItemAdapter : ListAdapter<MediaItemData, MediaViewHolder>(MediaItemData.diffCallback) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemMediaBinding.inflate(inflater, parent, false)
//        return MediaViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(
//        holder: MediaViewHolder,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//
//        val mediaItem = getItem(position)
//        var fullRefresh = payloads.isEmpty()
//
//        if (payloads.isNotEmpty()) {
//            payloads.forEach { payload ->
//                when (payload) {
//                    PLAYBACK_RES_CHANGED -> {
//                        holder.playbackState.setImageResource(mediaItem.playbackRes)
//                    }
//                    // If the payload wasn't understood, refresh the full item (to be safe).
//                    else -> fullRefresh = true
//                }
//            }
//        }
//
//        // Normally we only fully refresh the list item if it's being initially bound, but
//        // we might also do it if there was a payload that wasn't understood, just to ensure
//        // there isn't a stale item.
//        if (fullRefresh) {
//            holder.item = mediaItem
//            holder.titleView.text = mediaItem.title
//            holder.subtitleView.text = mediaItem.subtitle
//            holder.playbackState.setImageResource(mediaItem.playbackRes)
//
//            Glide.with(holder.albumArt)
//                .load(mediaItem.albumArtUri)
//                .into(holder.albumArt)
//        }
//    }
//
//    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
//        onBindViewHolder(holder, position, mutableListOf())
//    }
//}
//
//class MediaViewHolder(
//    binding: ItemMediaBinding
//) : RecyclerView.ViewHolder(binding.root) {
//
//    val titleView: TextView = binding.tvSongTitle
//    val subtitleView: TextView = binding.tvSinger
//    val albumArt: ImageView = binding.imgPlayingStatus
//    val playbackState: ImageView = binding.imgPlayingStatus
//
//    var item: MediaItemData? = null
//
//    init {
//        binding.root.setOnClickListener {
////            item?.let { itemClickedListener(it) }
//        }
//    }
//}
