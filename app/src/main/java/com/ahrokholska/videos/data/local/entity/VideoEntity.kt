package com.ahrokholska.videos.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val image: String,
    val duration: Int
)