package com.thymleaf.music.model.repository

import androidx.lifecycle.LiveData
import com.thymleaf.music.model.dao.MediaDao
import com.thymleaf.music.model.entity.MediaData

class MediaRepository(private val mediaDao: MediaDao) {

    fun getQueue(): LiveData<List<MediaData>> = mediaDao.getFavoriteMedia()

    fun getFavorite(): LiveData<List<MediaData>> = mediaDao.getFavoriteMedia()

    suspend fun insert(mediaData: MediaData) {
        mediaDao.insert(mediaData)
    }

    fun getRecent(): LiveData<List<MediaData>> = mediaDao.getRecent()

    companion object {

        @Volatile
        private var instance: MediaRepository? = null

        fun getInstance(mediaDao: MediaDao) =
                instance ?: synchronized(this) {
                    instance ?: MediaRepository(mediaDao)
                            .also { instance = it }
                }


    }

}