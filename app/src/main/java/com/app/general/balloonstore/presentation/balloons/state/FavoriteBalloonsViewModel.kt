package com.app.general.balloonstore.presentation.balloons.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.app.general.balloonstore.repository.FavoriteBalloonsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteBalloonsViewModel @Inject constructor(
    private val favoriteBalloonsPagingSource: FavoriteBalloonsPagingSource
) : ViewModel() {

    val favoriteBalloonsPager = Pager(
        config = PagingConfig(
            pageSize = FavoriteBalloonsPagingSource.PAGE_SIZE,
            enablePlaceholders = true,
        ),
        initialKey = FavoriteBalloonsPagingSource.STARTING_PAGE_INDEX,
        pagingSourceFactory = {
            favoriteBalloonsPagingSource
        }
    ).liveData.cachedIn(viewModelScope)
}