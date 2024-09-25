package com.ahrokholska.videos.presentation.screens.video

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.ahrokholska.videos.domain.model.VideoDetails
import com.ahrokholska.videos.domain.useCase.GetVideoDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoFullViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getVideoDetailsUseCase: GetVideoDetailsUseCase,
    val exoPlayer: ExoPlayer
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id") ?: 0
    private val _firstId = MutableStateFlow(id)
    private val _lastId = MutableStateFlow(id)
    private val _hasPrevious = MutableStateFlow(false)
    val hasPrevious = _hasPrevious.asStateFlow()
    private val _hasNext = MutableStateFlow(false)
    val hasNext = _hasNext.asStateFlow()
    private val loadedFirstVideo = MutableStateFlow(false)
    private val loadedLastVideo = MutableStateFlow(false)
    private var settingVideo = false

    init {
        viewModelScope.launch {
            val video = getVideoDetailsUseCase(id)
            launch {
                exoPlayer.setMediaItem(MediaItem.fromUri(video.url))
                exoPlayer.prepare()
            }
            _firstId.update { video.prevId ?: id }
            _lastId.update { video.nextId ?: id }
            checkPrevious(video)
            checkNext(video)
        }
    }

    private fun checkPrevious(video: VideoDetails) {
        val hasPrevious = video.prevId != null
        _hasPrevious.update { hasPrevious }
        if (!hasPrevious) {
            loadedFirstVideo.update { true }
        }
    }

    private fun checkNext(video: VideoDetails) {
        val hasNext = video.nextId != null
        _hasNext.update { hasNext }
        if (!hasNext) {
            loadedLastVideo.update { true }
        }
    }

    fun setPrevVideo() {
        if (settingVideo) return
        settingVideo = true
        viewModelScope.launch {
            fun setPrevItem() {
                exoPlayer.seekToPreviousMediaItem()
                _hasNext.update { true }
            }
            when (exoPlayer.currentMediaItemIndex) {
                0 -> {
                    val firstId = _firstId.value
                    val video = getVideoDetailsUseCase(firstId)
                    launch {
                        exoPlayer.addMediaItem(0, MediaItem.fromUri(video.url))
                        setPrevItem()
                    }
                    _firstId.update { video.prevId ?: firstId }
                    checkPrevious(video)
                }

                1 -> {
                    if (loadedFirstVideo.value) {
                        _hasPrevious.update { false }
                    }
                    setPrevItem()
                }

                else -> setPrevItem()
            }
            settingVideo = false
        }
    }

    fun setNextVideo() {
        if (settingVideo) return
        settingVideo = true
        viewModelScope.launch {
            fun setNextItem() {
                exoPlayer.seekToNextMediaItem()
                _hasPrevious.update { true }
            }
            when (exoPlayer.currentMediaItemIndex) {
                exoPlayer.mediaItemCount - 1 -> {
                    val lastId = _lastId.value
                    val video = getVideoDetailsUseCase(lastId)
                    launch {
                        exoPlayer.addMediaItem(MediaItem.fromUri(video.url))
                        setNextItem()
                    }
                    _lastId.update { video.nextId ?: lastId }
                    checkNext(video)
                }

                exoPlayer.mediaItemCount - 2 -> {
                    if (loadedLastVideo.value) {
                        _hasNext.update { false }
                    }
                    setNextItem()
                }

                else -> setNextItem()
            }
            settingVideo = false
        }
    }

    override fun onCleared() {
        exoPlayer.release()
        super.onCleared()
    }
}