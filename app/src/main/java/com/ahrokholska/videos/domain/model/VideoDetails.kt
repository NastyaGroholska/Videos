package com.ahrokholska.videos.domain.model

data class VideoDetails(
    val id: Int,
    val url: String,
    val imageURL: String,
    val durationSec: Int,
    val nextId: Int?,
    val prevId: Int?
)