package com.thymleaf.music.uamp.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thymleaf.music.R
import com.thymleaf.music.uamp.common.EMPTY_PLAYBACK_STATE
import com.thymleaf.music.uamp.common.MusicServiceConnection
import com.thymleaf.music.uamp.common.NOTHING_PLAYING
import com.thymleaf.music.uamp.media.extensions.albumArtUri
import com.thymleaf.music.uamp.media.extensions.currentPlayBackPosition
import com.thymleaf.music.uamp.media.extensions.displaySubtitle
import com.thymleaf.music.uamp.media.extensions.duration
import com.thymleaf.music.uamp.media.extensions.id
import com.thymleaf.music.uamp.media.extensions.isPlaying
import com.thymleaf.music.uamp.media.extensions.title

/**
 * [ViewModel] for [NowPlayingFragment] which displays the album art in full size.
 * It extends AndroidViewModel and uses the [Application]'s context to be able to reference string
 * resources.
 */
class NowPlayingFragmentViewModel(
        private val app: Application,
        musicServiceConnection: MusicServiceConnection
) : AndroidViewModel(app) {

    /**
     * Utility class used to represent the metadata necessary to display the
     * media item currently being played.
     */
    data class NowPlayingMetadata(
            val id: String,
            val albumArtUri: Uri,
            val title: String?,
            val subtitle: String?,
            val duration: String,
            val isPlaying: Boolean,
            val playState: Int
    ) {

        companion object {
            /**
             * Utility method to convert milliseconds to a display of minutes and seconds
             */
            fun timestampToMSS(context: Context, position: Long): String {
                val totalSeconds = Math.floor(position / 1E3).toInt()
                val minutes = totalSeconds / 60
                val remainingSeconds = totalSeconds - (minutes * 60)
                return if (position < 0) context.getString(R.string.duration_unknown)
                else context.getString(R.string.duration_format).format(minutes, remainingSeconds)
            }
        }
    }

    private var _playbackState: PlaybackStateCompat = EMPTY_PLAYBACK_STATE
    val playState = MutableLiveData<PlaybackStateCompat>()
    val mediaMetadata = MutableLiveData<NowPlayingMetadata>()
    val mediaPosition = MutableLiveData<Long>().apply {
        postValue(0L)
    }
    val mediaButtonRes = MutableLiveData<Int>().apply {
        postValue(R.drawable.ic_album_black_24dp)
    }

    private var updatePosition = true
    private val handler = Handler(Looper.getMainLooper())

    /**
     * When the session's [PlaybackStateCompat] changes, the [mediaItems] need to be updated
     * so the correct [MediaItemData.playbackRes] is displayed on the active item.
     * (i.e.: play/pause button or blank)
     */
    private val playbackStateObserver = Observer<PlaybackStateCompat> {
        _playbackState = it ?: EMPTY_PLAYBACK_STATE
        playState.postValue(_playbackState)
        val metadata = musicServiceConnection.nowPlaying.value ?: NOTHING_PLAYING
        updateState(_playbackState, metadata)
    }

    /**
     * When the session's [MediaMetadataCompat] changes, the [mediaItems] need to be updated
     * as it means the currently active item has changed. As a result, the new, and potentially
     * old item (if there was one), both need to have their [MediaItemData.playbackRes]
     * changed. (i.e.: play/pause button or blank)
     */
    private val mediaMetadataObserver = Observer<MediaMetadataCompat> {
        updateState(_playbackState, it)
    }

    /**
     * Because there's a complex dance between this [ViewModel] and the [MusicServiceConnection]
     * (which is wrapping a [MediaBrowserCompat] object), the usual guidance of using
     * [Transformations] doesn't quite work.
     *
     * Specifically there's three things that are watched that will cause the single piece of
     * [LiveData] exposed from this class to be updated.
     *
     * [MusicServiceConnection.playbackState] changes state based on the playback state of
     * the player, which can change the [MediaItemData.playbackRes]s in the list.
     *
     * [MusicServiceConnection.nowPlaying] changes based on the item that's being played,
     * which can also change the [MediaItemData.playbackRes]s in the list.
     */
    private val musicServiceConnection = musicServiceConnection.also {
        it.playbackState.observeForever(playbackStateObserver)
        it.nowPlaying.observeForever(mediaMetadataObserver)
        checkPlaybackPosition()
    }

    /**
     * Internal function that recursively calls itself every [POSITION_UPDATE_INTERVAL_MILLIS] ms
     * to check the current playback position and updates the corresponding LiveData object when it
     * has changed.
     */
    private fun checkPlaybackPosition(): Boolean = handler.postDelayed({
        val currPosition = _playbackState.currentPlayBackPosition
        if (mediaPosition.value != currPosition)
            mediaPosition.postValue(currPosition)
        if (updatePosition)
            checkPlaybackPosition()
    }, POSITION_UPDATE_INTERVAL_MILLIS)

    /**
     * Since we use [LiveData.observeForever] above (in [musicServiceConnection]), we want
     * to call [LiveData.removeObserver] here to prevent leaking resources when the [ViewModel]
     * is not longer in use.
     *
     * For more details, see the kdoc on [musicServiceConnection] above.
     */
    override fun onCleared() {
        super.onCleared()

        // Remove the permanent observers from the MusicServiceConnection.
        musicServiceConnection.playbackState.removeObserver(playbackStateObserver)
        musicServiceConnection.nowPlaying.removeObserver(mediaMetadataObserver)

        // Stop updating the position
        updatePosition = false
    }

    private fun updateState(
            playbackState: PlaybackStateCompat,
            mediaMetadata: MediaMetadataCompat
    ) {

        // Only update media item once we have duration available
        if (mediaMetadata.duration != 0L && mediaMetadata.id != null) {
            val nowPlayingMetadata = NowPlayingMetadata(
                    mediaMetadata.id!!,
                    mediaMetadata.albumArtUri,
                    mediaMetadata.title?.trim(),
                    mediaMetadata.displaySubtitle?.trim(),
                    NowPlayingMetadata.timestampToMSS(app, mediaMetadata.duration),
                    isPlaying = playbackState.isPlaying,
                    playState = playbackState.state
                    )
            this.mediaMetadata.postValue(nowPlayingMetadata)
        }

        // Update the media button resource ID
        mediaButtonRes.postValue(
                when (playbackState.isPlaying) {
                    true -> R.drawable.ic_pause_black_24dp
                    else -> R.drawable.ic_play_arrow_black_24dp
                }
        )
    }

    class Factory(
            private val app: Application,
            private val musicServiceConnection: MusicServiceConnection
    ) : ViewModelProvider.NewInstanceFactory() {

//        @Suppress("unchecked_cast")
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            return NowPlayingFragmentViewModel(app, musicServiceConnection) as T
//        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NowPlayingFragmentViewModel(app, musicServiceConnection) as T
        }
    }
}

private const val TAG = "NowPlayingFragmentVM"
private const val POSITION_UPDATE_INTERVAL_MILLIS = 100L
