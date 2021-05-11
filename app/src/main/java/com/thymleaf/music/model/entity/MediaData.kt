package com.thymleaf.music.model.entity

import androidx.room.*
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(tableName = "media_tb", indices = [Index(value = ["title", "artist"])])
data class MediaData(
        @NotNull
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "media_id")
        val mediaId: String,
        @NotNull
        val title: String,
        @NotNull
        val artist: String,
        @NotNull
        val playUri: String,
        val albumUri: String,
        val duration: Int?,
        @ColumnInfo(name = "least_play_time")
        val leastPlayTime: Calendar = Calendar.getInstance(),
        val playCount: Int? = 0,
        @ColumnInfo(name = "is_favorite")
        val isFavorite: Int? = 0,
        @ColumnInfo(name = "is_recent")
        val isRecent: Int? = 0,
        @ColumnInfo(name = "is_in_queue")
        val isInQueue: Int? = 0

) {
    override fun toString(): String {
        return "MediaData(id='$id', mediaId='$mediaId', title='$title', artist='$artist', playUri='$playUri', albumUri='$albumUri', duration=$duration, leastPlayTime=$leastPlayTime, playCount=$playCount, isFavorite=$isFavorite, isRecent=$isRecent, isInQueue=$isInQueue)"
    }
}
