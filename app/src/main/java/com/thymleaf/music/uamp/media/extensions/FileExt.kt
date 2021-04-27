package com.thymleaf.music.uamp.media.extensions

import android.content.ContentResolver
import android.net.Uri
import java.io.File

/**
 * This file contains extension methods for the java.io.File class.
 */

/**
 * Returns a Content Uri for the AlbumArtContentProvider
 */
fun File.asAlbumArtContentUri(): Uri {
    return Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .appendPath(this.path)
            .build()
}

private const val AUTHORITY = "com.example.android.uamp"
