package com.ahrokholska.videos.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class VideoFull(val id: Int) : Screen()
}