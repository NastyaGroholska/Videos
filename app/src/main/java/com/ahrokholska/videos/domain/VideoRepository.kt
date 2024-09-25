package com.ahrokholska.videos.domain

import com.ahrokholska.videos.domain.model.Video
import com.ahrokholska.videos.domain.model.VideoDetails
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    fun getVideos(): Flow<List<Video>>
    suspend fun refreshVideos(): Result<Unit>
    suspend fun getIsNoDataAvailable(): Boolean
    suspend fun getVideoDetails(id: Int): VideoDetails
}