package com.thymleaf.music.player


import android.content.Context
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes

class PlayerWrapper {

    companion object {
        private val uAmpAudioAttributes = AudioAttributes.Builder()
                .setContentType(C.CONTENT_TYPE_MUSIC)
                .setUsage(C.USAGE_MEDIA)
                .build()

        private fun getPlayer(context: Context, playerEventListener: Player.EventListener): ExoPlayer {
            return SimpleExoPlayer.Builder(context).build().apply {
                setAudioAttributes(uAmpAudioAttributes, true)
                setHandleAudioBecomingNoisy(true)
                addListener(playerEventListener)
            }
        }

        // For Singleton instantiation.
        @Volatile
        private var instance: ExoPlayer? = null

        fun getPlayerInstance(context: Context, playerEventListener: Player.EventListener) =
                instance ?: synchronized(this) {
                    instance ?: getPlayer(context, playerEventListener)
                }
    }

}


