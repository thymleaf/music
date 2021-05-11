package com.thymleaf.music.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thymleaf.music.model.entity.MediaData

@Dao
//interface MediaDao: BaseDao<MediaData> {
interface MediaDao {

    @Query("select * from media_tb where is_favorite = 1")
    fun getFavoriteMedia(): LiveData<List<MediaData>>

    @Query("select * from media_tb where is_in_queue = 1")
    fun getQueue(): LiveData<List<MediaData>>

    @Query("select * from media_tb where is_recent = 1 order by least_play_time")
    fun getRecent(): LiveData<List<MediaData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mediaData: MediaData): Long
}