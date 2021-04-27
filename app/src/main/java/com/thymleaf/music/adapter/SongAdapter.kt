package com.thymleaf.music.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.thymleaf.music.Song
import com.thymleaf.music.uamp.MediaItemData


class SongAdapter(layoutResId: Int) : BaseQuickAdapter<MediaItemData, BaseViewHolder>(layoutResId)  {


    override fun convert(holder: BaseViewHolder, item: MediaItemData) {
        
    }
}