package com.ahrokholska.videos.presentation.screens.video

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.ahrokholska.videos.domain.useCase.GetVideoDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoFullViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getVideoDetailsUseCase: GetVideoDetailsUseCase,
    val exoPlayer: ExoPlayer
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id") ?: 0

    init {
        viewModelScope.launch {
            val video = getVideoDetailsUseCase(id)
            exoPlayer.setMediaItem(MediaItem.fromUri(video.url))
            exoPlayer.prepare()
        }
    }

    override fun onCleared() {
        exoPlayer.release()
        super.onCleared()
    }
}