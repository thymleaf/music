package com.thymleaf.music.model.service

import com.library.common.base.BaseResponse
import com.thymleaf.music.model.entity.Track
import com.thymleaf.music.model.entity.Song
import com.thymleaf.music.model.entity.SongDetail
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaApi {

    @GET("artist/top/song")
    fun getSongs(@Query("id") id: String): Flowable<BaseResponse<List<Song>>>

    @GET("song/url")
    fun getPlayUrl(@Query("id") id: String): Flowable<BaseResponse<List<SongDetail>>>

    @GET("top/playlist/highquality")
    fun getAlbumList(): Flowable<BaseResponse<List<Track>>>

    @GET("personalized")
    fun getTracks(@Query("limit") limit: Int): Flowable<BaseResponse<List<Track>>>
}