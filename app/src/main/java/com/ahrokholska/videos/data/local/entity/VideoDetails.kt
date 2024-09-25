package com.ahrokholska.videos.data.local.entity

import androidx.room.Embedded

data class VideoDetails(
    @Embedded
    val video: VideoEntity,
    val nextId: Int?,
    val prevId: Int?
)