package com.ahrokholska.videos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ahrokholska.videos.data.local.entity.VideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("SELECT * FROM video")
    fun getAll(): Flow<List<VideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg videos: VideoEntity)

    @Query("DELETE FROM video")
    suspend fun deleteAll()

    @Transaction
    suspend fun refresh(videos: List<VideoEntity>) {
        deleteAll()
        insertAll(*videos.toTypedArray())
    }

    @Query("SELECT * FROM video WHERE id = :id")
    suspend fun getVideoDetail(id: Int): VideoEntity
}