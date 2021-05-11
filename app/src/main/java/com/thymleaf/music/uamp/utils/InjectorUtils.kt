/*
 * Copyright 2018 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thymleaf.music.uamp.utils

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import com.thymleaf.music.model.AppDatabase
import com.thymleaf.music.model.repository.MediaRepository
import com.thymleaf.music.uamp.common.MusicServiceConnection
import com.thymleaf.music.uamp.media.MusicService
import com.thymleaf.music.uamp.viewmodels.MainActivityViewModel
import com.thymleaf.music.uamp.viewmodels.MediaItemFragmentViewModel
import com.thymleaf.music.uamp.viewmodels.NowPlayingFragmentViewModel
import com.thymleaf.music.viewmodel.MediaDataViewModel

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    fun providerMediaDataViewModel(context: Context): MediaDataViewModel.Factory {
        return MediaDataViewModel.Factory(MediaRepository.getInstance(AppDatabase.getInstance(context).mediaDao()))
    }

    fun provideMusicServiceConnection(context: Context): MusicServiceConnection {
        return MusicServiceConnection.getInstance(
                context,
                ComponentName(context, MusicService::class.java)
        )
    }

    fun provideMainActivityViewModel(context: Context, bundle: Bundle? = null): MainActivityViewModel.Factory {
        val applicationContext = context.applicationContext
        val musicServiceConnection = provideMusicServiceConnection(applicationContext)
        return MainActivityViewModel.Factory(musicServiceConnection)
    }

    fun provideMediaItemFragmentViewModel(context: Context, mediaId: String, bundle: Bundle? = null)
            : MediaItemFragmentViewModel.Factory {
        val applicationContext = context.applicationContext
        val musicServiceConnection = provideMusicServiceConnection(applicationContext)
        return MediaItemFragmentViewModel.Factory(mediaId, musicServiceConnection)
    }

    fun provideNowPlayingFragmentViewModel(context: Context)
            : NowPlayingFragmentViewModel.Factory {
        val applicationContext = context.applicationContext
        val musicServiceConnection = provideMusicServiceConnection(applicationContext)
        return NowPlayingFragmentViewModel.Factory(
                applicationContext as Application, musicServiceConnection
        )
    }
}