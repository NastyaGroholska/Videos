package com.ahrokholska.videos.data.network

import retrofit2.http.GET

interface VideoService {
    @GET("/videos/popular")
    suspend fun getVideos(): VideoListResponse
}