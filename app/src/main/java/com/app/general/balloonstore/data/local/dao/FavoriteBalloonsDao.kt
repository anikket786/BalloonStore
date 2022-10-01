package com.app.general.balloonstore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.general.balloonstore.data.local.model.BalloonEntity

@Dao
interface FavoriteBalloonsDao {

    @Query("SELECT * FROM balloons order by rowID DESC LIMIT :limit OFFSET :startIndex")
    suspend fun getBalloons(limit: Int, startIndex: Int): List<BalloonEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBalloon(entity: BalloonEntity)
}