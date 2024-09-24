package com.ahrokholska.videos.data.mappers

import com.ahrokholska.videos.data.local.entity.VideoEntity
import com.ahrokholska.videos.data.network.VideoResponse

fun VideoResponse.toEntity() = VideoEntity(
    id = id,
    width = width,
    height = height,
    url = url,
    image = image,
    duration = duration
)