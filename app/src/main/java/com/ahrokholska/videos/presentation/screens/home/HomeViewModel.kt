package com.ahrokholska.videos.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.videos.domain.useCase.GetVideosUseCase
import com.ahrokholska.videos.domain.useCase.StartVideoDataCheckupUseCase
import com.ahrokholska.videos.presentation.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    startVideoDataCheckupUseCase: StartVideoDataCheckupUseCase,
    getVideosUseCase: GetVideosUseCase
) : ViewModel() {
    val videos = getVideosUseCase()
        .map { list -> list.map { it.toUi() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    init {
       viewModelScope.launch {
           startVideoDataCheckupUseCase()
       }
    }
}