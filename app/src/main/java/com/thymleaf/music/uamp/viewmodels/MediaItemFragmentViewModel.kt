package com.thymleaf.music.uamp.viewmodels

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaBrowserCompat.SubscriptionCallback
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.*
import com.thymleaf.music.R
import com.thymleaf.music.uamp.MediaItemData
import com.thymleaf.music.uamp.common.EMPTY_PLAYBACK_STATE
import com.thymleaf.music.uamp.common.MusicServiceConnection
import com.thymleaf.music.uamp.common.NOTHING_PLAYING
import com.thymleaf.music.uamp.media.extensions.id
import com.thymleaf.music.uamp.media.extensions.isPlaying

class MediaItemFragmentViewModel(
        private val mediaId: String,
        musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    /**
     * Use a backing property so consumers of mediaItems only get a [LiveData] instance so
     * they don't inadvertently modify it.
     */
    private val _mediaItems = MutableLiveData<MutableList<MediaItem>>()
    val mediaItems: LiveData<MutableList<MediaItem>> = _mediaItems
    private val _nowPlayingMediaId = MutableLiveData<Int>()
    val nowPlayingMediaId: LiveData<Int> = _nowPlayingMediaId


    private val _playbackState = MutableLiveData<PlaybackStateCompat>()
    private val _mediaMetadata = MutableLiveData<MediaMetadataCompat>()

    /**
     * Pass the status of the [MusicServiceConnection.networkFailure] through.
     */
    val networkError = Transformations.map(musicServiceConnection.networkFailure) { it }

    private val subscriptionCallback = object : SubscriptionCallback() {
        override fun onChildrenLoaded(parentId: String, children: List<MediaItem>) {

//            _mediaItems.postValue(children.toMutableList())
            _mediaItems.postValue(updateState(_playbackState.value,
                    _mediaMetadata.value, children.toMutableList()))
        }
    }

    /**
     * When the session's [PlaybackStateCompat] changes, the [mediaItems] need to be updated
     * so the correct [MediaItemData.playbackRes] is displayed on the active item.
     * (i.e.: play/pause button or blank)
     */
    private val playbackStateObserver = Observer<PlaybackStateCompat> {
        val playbackState = it ?: EMPTY_PLAYBACK_STATE
        _playbackState.postValue(playbackState)
        val metadata = musicServiceConnection.nowPlaying.value ?: NOTHING_PLAYING
        _mediaMetadata.postValue(metadata)
        if (metadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID) != null) {
            _mediaItems.postValue(updateState(playbackState, metadata))
        }
    }

    /**
     * When the session's [MediaMetadataCompat] changes, the [mediaItems] need to be updated
     * as it means the currently active item has changed. As a result, the new, and potentially
     * old item (if there was one), both need to have their [MediaItemData.playbackRes]
     * changed. (i.e.: play/pause button or blank)
     */
    private val mediaMetadataObserver = Observer<MediaMetadataCompat> {
        val playbackState = musicServiceConnection.playbackState.value ?: EMPTY_PLAYBACK_STATE
        _playbackState.postValue(playbackState)
        val metadata = it ?: NOTHING_PLAYING
        _mediaMetadata.postValue(metadata)
        if (metadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID) != null) {
            _mediaItems.postValue(updateState(playbackState, metadata))
        }
    }

    /**
     * Because there's a complex dance between this [ViewModel] and the [MusicServiceConnection]
     * (which is wrapping a [MediaBrowserCompat] object), the usual guidance of using
     * [Transformations] doesn't quite work.
     *
     * Specifically there's three things that are watched that will cause the single piece of
     * [LiveData] exposed from this class to be updated.
     *
     * [subscriptionCallback] (defined above) is called if/when the children of this
     * ViewModel's [mediaId] changes.
     *
     * [MusicServiceConnection.playbackState] changes state based on the playback state of
     * the player, which can change the [MediaItemData.playbackRes]s in the list.
     *
     * [MusicServiceConnection.nowPlaying] changes based on the item that's being played,
     * which can also change the [MediaItemData.playbackRes]s in the list.
     */
    private val musicServiceConnection = musicServiceConnection.also {
        it.subscribe(mediaId, subscriptionCallback)

        it.playbackState.observeForever(playbackStateObserver)
        it.nowPlaying.observeForever(mediaMetadataObserver)
    }

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

        // And then, finally, unsubscribe the media ID that was being watched.
        musicServiceConnection.unsubscribe(mediaId, subscriptionCallback)
    }

    private fun getResourceForMediaId(mediaId: String): Int {
        val isActive = mediaId == musicServiceConnection.nowPlaying.value?.id
        val isPlaying = musicServiceConnection.playbackState.value?.isPlaying ?: false
        return when {
            !isActive -> NO_RES
            isPlaying -> R.drawable.ic_pause_black_24dp
            else -> R.drawable.ic_play_arrow_black_24dp
        }
    }

    private fun updateState(
            playbackState: PlaybackStateCompat?,
            mediaMetadata: MediaMetadataCompat?,
            curMediaItems: MutableList<MediaItem>? = mediaItems.value
    ): MutableList<MediaItem>? {

        curMediaItems?.forEachIndexed() { index, item ->
            val isPlaying = item.mediaId == musicServiceConnection.nowPlaying.value?.id
            if (isPlaying)
            {
                _nowPlayingMediaId.postValue(index)
            }
            item.description.extras.let {
                playbackState?.let { it1 -> it?.putInt(KEY_PLAY_STATE, it1.state) }
                it?.putBoolean(KEY_IS_PLAYING, isPlaying)
            }
        }

        return curMediaItems
    }

    class Factory(
            private val mediaId: String,
            private val musicServiceConnection: MusicServiceConnection
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MediaItemFragmentViewModel(mediaId, musicServiceConnection) as T
        }
    }
}

private const val TAG = "MediaItemFragmentVM"
private const val NO_RES = 0
const val KEY_IS_PLAYING = "key_is_playing"
const val KEY_PLAY_STATE = "key_play_state"
