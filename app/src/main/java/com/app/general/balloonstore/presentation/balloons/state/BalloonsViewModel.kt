package com.app.general.balloonstore.presentation.balloons.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.app.general.balloonstore.BalloonListQuery
import com.app.general.balloonstore.repository.BalloonsPagingSource
import com.app.general.balloonstore.repository.BalloonsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalloonsViewModel @Inject constructor(
    private val balloonListPagingSource: BalloonsPagingSource,
    private val balloonsRepository: BalloonsRepository
) : ViewModel() {

    val balloonsPager = Pager(
        config = PagingConfig(
            pageSize = BalloonsPagingSource.PAGE_SIZE,
            enablePlaceholders = true,
        ),
        initialKey = null,
        pagingSourceFactory = {
            balloonListPagingSource
        }
    ).liveData.cachedIn(viewModelScope)

    fun saveBalloonWithCustomMessage(node: BalloonListQuery.Node, message: String) =
        viewModelScope.launch {
            balloonsRepository.saveBalloonWithCustomMessage(node, message)
        }
}