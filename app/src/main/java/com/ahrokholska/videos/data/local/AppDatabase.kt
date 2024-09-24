package com.ahrokholska.videos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahrokholska.videos.data.local.dao.VideoDao
import com.ahrokholska.videos.data.local.entity.VideoEntity

@Database(entities = [VideoEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}