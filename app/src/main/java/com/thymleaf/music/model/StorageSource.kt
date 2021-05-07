
package com.thymleaf.music.model

import android.content.Context
import android.support.v4.media.MediaMetadataCompat
import com.thymleaf.music.uamp.media.library.AbstractMusicSource
import com.thymleaf.music.uamp.media.library.STATE_INITIALIZED
import com.thymleaf.music.uamp.media.library.STATE_INITIALIZING
import com.thymleaf.music.util.StorageMediaResolver.getStorageMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StorageSource(private val context: Context) : AbstractMusicSource() {

    init {
        state = STATE_INITIALIZING
    }

    override fun getMediaItems(): List<MediaMetadataCompat> {
        return mediaItem
    }

    var mediaItem: List<MediaMetadataCompat>  = emptyList()

    override fun iterator(): Iterator<MediaMetadataCompat> = mediaItem.iterator()

    override suspend fun load() {
        loadStorageMedia(context).let { item ->
            mediaItem = item
            state = STATE_INITIALIZED
        }
    }


    /**
     * Function to connect to a remote URI and download/process the JSON file that corresponds to
     * [MediaMetadataCompat] objects.
     */
    private suspend fun loadStorageMedia(context: Context): List<MediaMetadataCompat> {
        return withContext(Dispatchers.IO) {
            getStorageMedia(context)
        }
    }
}


