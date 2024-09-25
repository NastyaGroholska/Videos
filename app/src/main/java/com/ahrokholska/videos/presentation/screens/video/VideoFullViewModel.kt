package com.ahrokholska.videos.presentation.screens.video

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
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
    getVideoDetailsUseCase: GetVideoDetailsUseCase,
    val exoPlayer: ExoPlayer
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id") ?: 0
    private val _hasPrevious = MutableStateFlow(false)
    val hasPrevious = _hasPrevious.asStateFlow()
    private val _hasNext = MutableStateFlow(true)
    val hasNext = _hasNext.asStateFlow()

    init {
        viewModelScope.launch {
            val video = getVideoDetailsUseCase(id)
            launch {
                exoPlayer.setMediaItem(
                    MediaItem.fromUri(video.url)
                )
                exoPlayer.prepare()
            }
            _hasPrevious.update { video.prevId != null }
            _hasNext.update { video.nextId != null }
        }
    }

    override fun onCleared() {
        exoPlayer.release()
        super.onCleared()
    }
}