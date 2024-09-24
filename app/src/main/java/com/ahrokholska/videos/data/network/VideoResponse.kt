package com.ahrokholska.videos.data.network

data class VideoResponse(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val image: String,
    val duration: Int
)