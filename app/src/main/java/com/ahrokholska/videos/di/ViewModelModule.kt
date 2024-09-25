package com.ahrokholska.videos.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @OptIn(UnstableApi::class)
    @Provides
    @ViewModelScoped
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer =
        ExoPlayer.Builder(context).build()
}