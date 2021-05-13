package com.thymleaf.music.player

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator

class PlayerQueueNavigator(
        mediaSession: MediaSessionCompat, private val preparer: ExoPlaybackPreparer
) : TimelineQueueNavigator(mediaSession, 1000) {
    override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat =
            preparer.currentPlaylistItems[windowIndex].description
}