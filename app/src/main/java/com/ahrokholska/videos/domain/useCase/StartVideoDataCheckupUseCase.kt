package com.ahrokholska.videos.domain.useCase

import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ahrokholska.videos.data.work.GetDataWorker
import javax.inject.Inject

class StartVideoDataCheckupUseCase @Inject constructor(private val workManager: WorkManager) {
    operator fun invoke() = workManager.enqueueUniqueWork(
        GetDataWorker.NAME,
        ExistingWorkPolicy.KEEP,
        OneTimeWorkRequestBuilder<GetDataWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
    )
}