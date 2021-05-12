package com.thymleaf.music.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.thymleaf.music.R
import com.thymleaf.music.model.entity.Song

class SongAdapter(layout: Int) : BaseQuickAdapter<Song, BaseViewHolder>(layout) {
    override fun convert(holder: BaseViewHolder, item: Song) {

        addChildClickViewIds(R.id.item_container)
        val artist = item.ar.map {
            it.name
        }.reduce{acc, s -> "$acc/$s " }

        val album = item.al.name

        holder.setText(R.id.tv_title, item.name)
                .setText(R.id.tv_subtitle, "$artist - $album")

    }
}