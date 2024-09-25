package com.ahrokholska.videos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ahrokholska.videos.data.local.entity.VideoDetails
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

    @Query(
        "SELECT * FROM  " +
                "(SELECT * FROM video WHERE id = :id) " +
                "LEFT JOIN  (SELECT id as nextId FROM video WHERE id > :id LIMIT 1)" +
                "LEFT JOIN  (SELECT id as prevId FROM video WHERE id < :id ORDER BY id DESC LIMIT 1)"
    )
    suspend fun getVideoDetail(id: Int): VideoDetails
}