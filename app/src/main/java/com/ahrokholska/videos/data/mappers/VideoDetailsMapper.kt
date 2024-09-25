package com.ahrokholska.videos.data.mappers

import com.ahrokholska.videos.data.local.entity.VideoDetails

fun VideoDetails.toDomain() = com.ahrokholska.videos.domain.model.VideoDetails(
    id = video.id,
    url = video.url,
    imageURL = video.image,
    durationSec = video.duration,
    nextId = nextId,
    prevId = prevId
)