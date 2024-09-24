package com.ahrokholska.videos.presentation.model

data class VideoUI(
    val id: Int,
    val url: String,
    val imageURL: String,
    val durationFormatted: String
)