package com.thymleaf.music.adapter

import android.content.Context
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.library.common.utils.ImageLoaderUtil
import com.thymleaf.music.R
import com.thymleaf.music.model.entity.Track

class TrackAdapter(context: Context,layoutResId: Int) : BaseQuickAdapter<Track, BaseViewHolder>(layoutResId) {
    override fun convert(holder: BaseViewHolder, item: Track) {
        addChildClickViewIds(R.id.item_container)

        holder.setText(R.id.tv_title, item.name)
                .setText(R.id.tv_subtitle, item.copywriter)
                .setText(R.id.play_count, item.playCount.toString())

        val img = holder.getView<ImageView>(R.id.track_pic)
        ImageLoaderUtil.display(context, img, item.picUrl)
    }
}