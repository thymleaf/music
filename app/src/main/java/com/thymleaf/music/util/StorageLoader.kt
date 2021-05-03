package com.thymleaf.music.util

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.text.TextUtils
import com.thymleaf.music.uamp.media.extensions.*
//import com.cyl.musiclake.bean.Album
//import com.cyl.musiclake.bean.Artist
//import com.cyl.musiclake.bean.Music
//import com.cyl.musiclake.data.db.DaoLitepal
//import com.cyl.musiclake.common.Constants
//import com.cyl.musiclake.utils.CoverLoader
//import com.cyl.musiclake.utils.LogUtil
//import org.litepal.LitePal
import java.util.concurrent.TimeUnit


object StorageLoader {

    fun getStorageMedia(context: Context): MutableList<MediaMetadataCompat> {
        val results = mutableListOf<MediaMetadataCompat>()
        val cursor: Cursor = getAudioCursor(context) ?: return results
        try {
            while (cursor.moveToNext()) {
                results.add(MediaMetadataCompat.Builder().from(cursor).build())
            }
            cursor.close()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return results.filter { it.description.subtitle != "<unknown>" }.toMutableList()
    }

    fun MediaMetadataCompat.Builder.from(cursor: Cursor): MediaMetadataCompat.Builder {
        val idTmp = cursor.getLong(0)
        id = idTmp.toString()
        title = cursor.getString(1)
        artist = cursor.getString(2)
        album = cursor.getString(3)
        duration = TimeUnit.SECONDS.toMillis(cursor.getLong(4))
        flag = MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
        displayTitle = cursor.getString(1)
        displaySubtitle = cursor.getString(2)
        displayDescription = cursor.getString(3)
        displayIconUri = cursor.getString(3)
        downloadStatus = MediaDescriptionCompat.STATUS_NOT_DOWNLOADED
        flag = MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
        mediaUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, idTmp)
                .toString()
        return this
    }

    /**
     * 搜素本地音乐
     */
    private fun getAudioCursor(context: Context,
                               selection: String? = null,
                               paramArrayOfString: Array<String>? = null,
                               sortOrder: String? = MediaStore.Audio.Media.DEFAULT_SORT_ORDER): Cursor? {
        var selectionStatement = "duration>60000 AND is_music=1 AND title != '' AND title NOT Like '%unknown%'"

        if (!TextUtils.isEmpty(selection)) {
            selectionStatement = "$selectionStatement AND $selection"
        }

        val projection = arrayOf("_id",
                "title",
                "artist",
                "album",
                "duration",
                "is_music")


        return context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selectionStatement, paramArrayOfString, sortOrder)
    }
}
