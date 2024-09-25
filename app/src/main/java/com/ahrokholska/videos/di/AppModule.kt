package com.ahrokholska.videos.di

import android.content.Context
import androidx.room.Room
import com.ahrokholska.videos.data.VideoRepositoryImpl
import com.ahrokholska.videos.data.local.AppDatabase
import com.ahrokholska.videos.data.network.VideoService
import com.ahrokholska.videos.domain.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .client(OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "R3gE0RFnukarQ7KtPD6pr39qI32eMDuUs6sTOUPwRZPIYZksrBM1YyqI"
                    )
                    .build()
            )
        }).build())
        .baseUrl("https://api.pexels.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideVideoService(retrofit: Retrofit) = retrofit.create<VideoService>()

    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "app_database"
    ).build()

    @Provides
    @Singleton
    fun provideVideoDao(db: AppDatabase) = db.videoDao()

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppBindModule {
        @Binds
        @Singleton
        fun bindVideoRepository(repository: VideoRepositoryImpl): VideoRepository
    }
}