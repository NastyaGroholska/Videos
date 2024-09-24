package com.ahrokholska.videos.data.mappers

import com.ahrokholska.videos.data.local.entity.VideoEntity
import com.ahrokholska.videos.domain.model.Video

fun VideoEntity.toDomain() = Video(
    id = id,
    url = url,
    imageURL = image,
    durationSec = duration
)