package com.ahrokholska.videos.data.network

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val image: String,
    val duration: Int,
    @SerializedName("video_files") val files: List<VideoFile>
)