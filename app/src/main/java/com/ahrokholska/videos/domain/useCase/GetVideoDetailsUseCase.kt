package com.ahrokholska.videos.domain.useCase

import com.ahrokholska.videos.domain.VideoRepository
import javax.inject.Inject

class GetVideoDetailsUseCase @Inject constructor(private val repository: VideoRepository) {
    suspend operator fun invoke(id: Int) = repository.getVideoDetails(id)
}