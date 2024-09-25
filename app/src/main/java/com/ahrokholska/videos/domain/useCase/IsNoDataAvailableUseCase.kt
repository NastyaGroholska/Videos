package com.ahrokholska.videos.domain.useCase

import com.ahrokholska.videos.domain.VideoRepository
import javax.inject.Inject

class IsNoDataAvailableUseCase @Inject constructor(private val repository: VideoRepository) {
    suspend operator fun invoke(): Boolean = repository.getIsNoDataAvailable()
}