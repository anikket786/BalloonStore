package com.app.general.balloonstore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.general.balloonstore.data.local.dao.FavoriteBalloonsDao
import com.app.general.balloonstore.data.local.model.BalloonEntity

@Database(
    entities = [BalloonEntity::class],
    version = 3,
    exportSchema = false
)
abstract class BalloonStoreDb : RoomDatabase() {
    abstract val favoriteBalloonsDao: FavoriteBalloonsDao

    companion object {
        const val DB_NAME = "balloon_store_db"
    }
}