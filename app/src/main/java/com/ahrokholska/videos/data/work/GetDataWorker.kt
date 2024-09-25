package com.ahrokholska.videos.data.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ahrokholska.videos.domain.useCase.IsNoDataAvailableUseCase
import com.ahrokholska.videos.domain.useCase.RefreshVideosUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class GetDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val isNoDataAvailableUseCase: IsNoDataAvailableUseCase,
    private val refreshVideosUseCase: RefreshVideosUseCase
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        if (isNoDataAvailableUseCase()) {
            refreshVideosUseCase().onSuccess {
                return Result.success()
            }.onFailure {
                return Result.failure()
            }
        }
        return Result.success()
    }

    companion object {
        const val NAME = "GetDataWork"
    }
}