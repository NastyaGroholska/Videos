package com.ahrokholska.videos.data.network

import com.google.gson.annotations.SerializedName

data class VideoListResponse(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    val url: String,
    val videos: List<VideoResponse>
)