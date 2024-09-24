package com.ahrokholska.videos.domain.useCase

import com.ahrokholska.videos.domain.VideoRepository
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(private val repository: VideoRepository) {
    operator fun invoke() = repository.getVideos()
}