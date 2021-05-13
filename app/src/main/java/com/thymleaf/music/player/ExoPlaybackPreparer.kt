package com.thymleaf.music.player

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.thymleaf.music.uamp.media.KEY_PLAY_MEDIA_POSITION
import com.thymleaf.music.uamp.media.KEY_PLAY_MEDIA_QUEUE
import com.thymleaf.music.uamp.media.MEDIA_DESCRIPTION_EXTRAS_START_PLAYBACK_POSITION_MS
import com.thymleaf.music.uamp.media.extensions.toMediaSource
import com.thymleaf.music.uamp.media.library.AbstractMusicSource
import com.thymleaf.music.util.StorageMediaResolver.from

class ExoPlaybackPreparer(private val context: Context, private val player: ExoPlayer) : MediaSessionConnector.PlaybackPreparer {

    var currentPlaylistItems: List<MediaMetadataCompat> = emptyList()

    /**
     * UAMP supports preparing (and playing) from search, as well as media ID, so those
     * capabilities are declared here.
     *
     * TODO: Add support for ACTION_PREPARE and ACTION_PLAY, which mean "prepare/play something".
     */
    override fun getSupportedPrepareActions(): Long =
            PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH or
                    PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH

    override fun onPrepare(playWhenReady: Boolean) {

    }

    override fun onPrepareFromMediaId(
            mediaId: String,
            playWhenReady: Boolean,
            extras: Bundle?
    ) {

        val playbackStartPositionMs =
                extras?.getLong(MEDIA_DESCRIPTION_EXTRAS_START_PLAYBACK_POSITION_MS, C.TIME_UNSET)
                        ?: C.TIME_UNSET

        val position: Int = extras?.getInt(KEY_PLAY_MEDIA_POSITION) ?: 0
        val mediaItems: ArrayList<MediaBrowserCompat.MediaItem>? = (extras?.getParcelableArrayList(KEY_PLAY_MEDIA_QUEUE))

        val playList = mediaItems?.map {
            MediaMetadataCompat.Builder().from(it).build()
        }?.toList() ?: emptyList()

        preparePlaylist(
                playList,
                position,
                playWhenReady,
                playbackStartPositionMs
        )
    }

    /**
     * This method is used by the Google Assistant to respond to requests such as:
     * - Play Geisha from Wake Up on UAMP
     * - Play electronic music on UAMP
     * - Play music on UAMP
     *
     * For details on how search is handled, see [AbstractMusicSource.search].
     */
    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) {

    }

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) = Unit

    override fun onCommand(
            player: Player,
            controlDispatcher: ControlDispatcher,
            command: String,
            extras: Bundle?,
            cb: ResultReceiver?
    ) = false

    private fun preparePlaylist(
            metadataList: List<MediaMetadataCompat>,
            position: Int,
            playWhenReady: Boolean,
            playbackStartPositionMs: Long
    ) {
        currentPlaylistItems = metadataList
        player.playWhenReady = playWhenReady
        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, PLAY_USER_AGENT), null)
        val mediaSource = metadataList.toMediaSource(dataSourceFactory)
        player.prepare(mediaSource)
        player.seekTo(position, playbackStartPositionMs)
    }
}
private const val PLAY_USER_AGENT = "play.next"