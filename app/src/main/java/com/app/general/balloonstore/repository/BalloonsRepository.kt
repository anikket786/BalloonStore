package com.app.general.balloonstore.repository

import com.app.general.balloonstore.BalloonListQuery
import com.app.general.balloonstore.data.local.dao.FavoriteBalloonsDao
import com.app.general.balloonstore.data.local.model.BalloonEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class BalloonsRepository @Inject constructor(
    private val favoriteBalloonsDao: FavoriteBalloonsDao
) {
    suspend fun saveBalloonWithCustomMessage(node: BalloonListQuery.Node, message: String) {
        val entity = BalloonEntity.nodeToEntity(node)
        entity.customMessage = message
        withContext(Dispatchers.IO) {
            favoriteBalloonsDao.saveBalloon(entity)
        }
    }
}