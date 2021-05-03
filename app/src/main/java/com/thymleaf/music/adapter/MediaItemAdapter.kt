package com.thymleaf.music.adapter

import android.support.v4.media.MediaBrowserCompat
import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.thymleaf.music.R

class MediaItemAdapter(@LayoutRes private val layoutResId: Int) :
        BaseQuickAdapter<MediaBrowserCompat.MediaItem, BaseViewHolder>(layoutResId)
{
    override fun convert(holder: BaseViewHolder, item: MediaBrowserCompat.MediaItem) {
        holder.setText(R.id.tv_title, item.description.title)
                .setText(R.id.tv_subtitle, item.description.subtitle)
    }

}