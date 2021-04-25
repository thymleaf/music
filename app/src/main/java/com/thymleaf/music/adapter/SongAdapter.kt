package com.thymleaf.music.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.thymleaf.music.Song


class SongAdapter(layoutResId: Int) : BaseQuickAdapter<Song, BaseViewHolder>(layoutResId)  {


    override fun convert(holder: BaseViewHolder, item: Song) {
        
    }
}