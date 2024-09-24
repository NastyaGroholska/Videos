package com.ahrokholska.videos.domain

import com.ahrokholska.videos.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    fun getVideos(): Flow<List<Video>>
    suspend fun refreshVideos(): Result<Unit>
}