package com.thymleaf.music.uamp.viewmodels

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
//import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import com.thymleaf.music.uamp.MediaItemData
import com.thymleaf.music.uamp.common.MusicServiceConnection
import com.thymleaf.music.uamp.media.KEY_PLAY_MEDIA_POSITION
import com.thymleaf.music.uamp.media.KEY_PLAY_MEDIA_QUEUE
import com.thymleaf.music.uamp.media.extensions.id
import com.thymleaf.music.uamp.media.extensions.isPlayEnabled
import com.thymleaf.music.uamp.media.extensions.isPlaying
import com.thymleaf.music.uamp.media.extensions.isPrepared
import com.thymleaf.music.uamp.utils.Event

/**
 * Small [ViewModel] that watches a [MusicServiceConnection] to become connected
 * and provides the root/initial media ID of the underlying [MediaBrowserCompat].
 */
class MainActivityViewModel(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    val rootMediaId: LiveData<String> =

        musicServiceConnection.isConnected.map { isConnected ->
            (if (isConnected) {
                musicServiceConnection.rootMediaId
            } else {
                null
            })!!
        }

//        Transformations.map(musicServiceConnection.isConnected) { isConnected ->
//            if (isConnected) {
//                musicServiceConnection.rootMediaId
//            } else {
//                null
//            }
//        }

    /**
     * [navigateToMediaItem] acts as an "event", rather than state. [Observer]s
     * are notified of the change as usual with [LiveData], but only one [Observer]
     * will actually read the data. For more information, check the [Event] class.
     */
    val navigateToMediaItem: LiveData<Event<String>> get() = _navigateToMediaItem
    private val _navigateToMediaItem = MutableLiveData<Event<String>>()

    /**
     * This [LiveData] object is used to notify the MainActivity that the main
     * content fragment needs to be swapped. Information about the new fragment
     * is conveniently wrapped by the [Event] class.
     */
    val navigateToFragment: LiveData<Event<FragmentNavigationRequest>> get() = _navigateToFragment
    private val _navigateToFragment = MutableLiveData<Event<FragmentNavigationRequest>>()


    /**
     * Convenience method used to swap the fragment shown in the main activity
     *
     * @param fragment the fragment to show
     * @param backStack if true, add this transaction to the back stack
     * @param tag the name to use for this fragment in the stack
     */
    fun showFragment(fragment: Fragment, backStack: Boolean = true, tag: String? = null) {
        _navigateToFragment.value = Event(FragmentNavigationRequest(fragment, backStack, tag))
    }

    /**
     * This method takes a [MediaItemData] and does one of the following:
     * - If the item is *not* the active item, then play it directly.
     * - If the item *is* the active item, check whether "pause" is a permitted command. If it is,
     *   then pause playback, otherwise send "play" to resume playback.
     */
    fun playMedia(position: Int, pauseAllowed: Boolean = true,
                  queue: MutableList<MediaBrowserCompat.MediaItem> = mutableListOf()) {

        val mediaItem: MediaBrowserCompat.MediaItem = queue[position]
        val nowPlaying = musicServiceConnection.nowPlaying.value
        val transportControls = musicServiceConnection.transportControls

        val isPrepared = musicServiceConnection.playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaItem.mediaId == nowPlaying?.id) {
            musicServiceConnection.playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying ->
                        if (pauseAllowed) transportControls.pause() else Unit
                    playbackState.isPlayEnabled -> transportControls.play()
                    else -> {
                        Log.w(
                            TAG, "Playable item clicked but neither play nor pause are enabled!" +
                                    " (mediaId=${mediaItem.mediaId})"
                        )
                    }
                }
            }
        } else {
            transportControls.playFromMediaId(mediaItem.mediaId, Bundle().apply {
                putInt(KEY_PLAY_MEDIA_POSITION, position)
                putParcelableArrayList(KEY_PLAY_MEDIA_QUEUE, queue.toCollection(arrayListOf()))
            })
        }
    }

    private fun playMediaId(mediaId: String) {
        val nowPlaying = musicServiceConnection.nowPlaying.value
        val transportControls = musicServiceConnection.transportControls

        val isPrepared = musicServiceConnection.playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaId == nowPlaying?.id) {
            musicServiceConnection.playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> transportControls.pause()
                    playbackState.isPlayEnabled -> transportControls.play()
                    else -> {
                        Log.w(
                            TAG, "Playable item clicked but neither play nor pause are enabled!" +
                                    " (mediaId=$mediaId)"
                        )
                    }
                }
            }
        } else {
            transportControls.playFromMediaId(mediaId, null)
        }
    }

    class Factory(
        private val musicServiceConnection: MusicServiceConnection
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainActivityViewModel(musicServiceConnection) as T
        }

//        @Suppress("unchecked_cast")
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            return MainActivityViewModel(musicServiceConnection) as T
//        }
    }
}

/**
 * Helper class used to pass fragment navigation requests between MainActivity
 * and its corresponding ViewModel.
 */
data class FragmentNavigationRequest(
    val fragment: Fragment,
    val backStack: Boolean = false,
    val tag: String? = null
)

private const val TAG = "MainActivityVM"
