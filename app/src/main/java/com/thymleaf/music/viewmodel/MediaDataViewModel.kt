package com.thymleaf.music.viewmodel

import androidx.lifecycle.*
import com.thymleaf.music.model.entity.MediaData
import com.thymleaf.music.model.repository.MediaRepository
import kotlinx.coroutines.launch

class MediaDataViewModel(private val repository: MediaRepository) : ViewModel() {

    val mediaQueue = MutableLiveData<List<MediaData>>()

    fun getQueue(): LiveData<List<MediaData>> {
        return repository.getQueue()
    }

    fun getFavorite(): LiveData<List<MediaData>> {
        return repository.getFavorite()
    }

    fun addRecent(mediaData: MediaData) {
        viewModelScope.launch {
            repository.insert(mediaData)
        }
    }

    fun getRecent(): LiveData<List<MediaData>> {
        return repository.getRecent()
    }

    class Factory(
            private val repository: MediaRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MediaDataViewModel(repository) as T
        }
    }

}