package com.ahrokholska.videos.data

import com.ahrokholska.videos.data.local.dao.VideoDao
import com.ahrokholska.videos.data.mappers.toDomain
import com.ahrokholska.videos.data.mappers.toEntity
import com.ahrokholska.videos.data.network.VideoService
import com.ahrokholska.videos.domain.VideoRepository
import com.ahrokholska.videos.domain.model.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class VideoRepositoryImpl @Inject constructor(
    private val videoService: VideoService,
    private val videoDao: VideoDao
) : VideoRepository {

    override fun getVideos(): Flow<List<Video>> =
        videoDao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun refreshVideos(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val result = videoService.getVideos().videos
            videoDao.insertAll(*result.map { it.toEntity() }.toTypedArray())
            Result.success(Unit)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }
}