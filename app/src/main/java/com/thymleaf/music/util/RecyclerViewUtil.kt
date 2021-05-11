package com.thymleaf.music.util

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.thymleaf.music.R

object RecyclerViewUtil {
    fun setItemDividerDuration(context: Context?, recyclerView: RecyclerView,
                               dividerRes: Int = R.drawable.inset_recycler_divider) {
        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        ContextCompat.getDrawable(context!!, dividerRes)?.let { dividerItemDecoration.setDrawable(it) }
        recyclerView.addItemDecoration(dividerItemDecoration)
    }
}