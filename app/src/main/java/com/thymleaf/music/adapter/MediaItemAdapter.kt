package com.thymleaf.music.adapter

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.PlaybackStateCompat.*
import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.thymleaf.music.R
import com.thymleaf.music.uamp.viewmodels.KEY_IS_PLAYING
import com.thymleaf.music.uamp.viewmodels.KEY_PLAY_STATE

class MediaItemAdapter(@LayoutRes private val layoutResId: Int) :
        BaseQuickAdapter<MediaBrowserCompat.MediaItem, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: MediaBrowserCompat.MediaItem) {

        holder.setText(R.id.tv_title, item.description.title)
                .setText(R.id.tv_subtitle, item.description.subtitle)

        addChildClickViewIds(R.id.item_container)

        item.description.extras?.let {
            val isPlaying = it.getBoolean(KEY_IS_PLAYING, false)
            holder.setVisible(R.id.play_count, isPlaying)
                    .setVisible(R.id.track_pic, isPlaying)
                    .setVisible(R.id.view_status_top, isPlaying)
                    .setVisible(R.id.view_status_bottom, isPlaying)
                    .setVisible(R.id.view_status_right, isPlaying)

            when (it.getInt(KEY_PLAY_STATE, 0)) {
                STATE_STOPPED -> {
                    holder.setBackgroundResource(R.id.play_count, R.drawable.ic_play_arrow_black_24dp)
                }
                STATE_PAUSED -> {
                    holder.setBackgroundResource(R.id.play_count, R.drawable.ic_pause_black_24dp)
                }
                STATE_PLAYING -> {
                    holder.setBackgroundResource(R.id.play_count, R.drawable.outline_equalizer_24)
                }
                else -> {
                    holder.setVisible(R.id.play_count, false)
                }
            }
        }
    }

}