package com.ahrokholska.videos.domain.model

data class Video(
    val id: Int,
    val url: String,
    val imageURL: String,
    val durationSec: Int
)