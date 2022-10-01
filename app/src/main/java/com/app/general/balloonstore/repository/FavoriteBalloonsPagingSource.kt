package com.app.general.balloonstore.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.general.balloonstore.common.errors.LoadException
import com.app.general.balloonstore.data.local.dao.FavoriteBalloonsDao
import com.app.general.balloonstore.data.local.model.BalloonEntity
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FavoriteBalloonsPagingSource @Inject constructor(
    private val favoriteBalloonsDao: FavoriteBalloonsDao
) : PagingSource<Int, BalloonEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BalloonEntity> {
        return try {
            val favoriteList = params.key?.let { favoriteBalloonsDao.getBalloons(PAGE_SIZE, it) }
            val prevKey = if (params.key == STARTING_PAGE_INDEX) null else params.key
            val nextKey = params.key?.plus(5)

            if (favoriteList != null) {
                LoadResult.Page(
                    data = favoriteList,
                    prevKey = prevKey,
                    nextKey = nextKey,
                )
            } else {
               LoadResult.Error(LoadException())
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BalloonEntity>): Int {
        return STARTING_PAGE_INDEX
    }

    companion object {
        const val STARTING_PAGE_INDEX = 0
        const val PAGE_SIZE = 5
    }
}