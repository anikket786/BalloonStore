package com.app.general.balloonstore.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.app.general.balloonstore.BalloonListQuery
import com.app.general.balloonstore.common.errors.LoadException
import com.app.general.balloonstore.common.errors.NetworkException
import com.app.general.balloonstore.type.FilterInput
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class BalloonsPagingSource @Inject constructor(
    private val apolloClient: ApolloClient,
) : PagingSource<String, BalloonListQuery.Edge>() {

    var filter: FilterInput? = null

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, BalloonListQuery.Edge> {
        return try {
            val response = loadNextPage(params.key)
            val balloons = response.data?.balloons
            val prevKey = params.key
            val nextKey = if (balloons?.pageInfo?.hasNextPage == true) {
                balloons.pageInfo.endCursor
            } else {
                null
            }

            if (balloons?.edges != null) {
                LoadResult.Page(
                    data = balloons.edges,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(LoadException())
            }
        } catch (e: Exception) {
            when (e) {
                is ApolloNetworkException -> {
                    LoadResult.Error(NetworkException())
                }
                else -> {
                    LoadResult.Error(e)
                }
            }
        }
    }

    override fun getRefreshKey(state: PagingState<String, BalloonListQuery.Edge>): String? {
        return null
    }

    private suspend fun loadNextPage(key: String?) : ApolloResponse<BalloonListQuery.Data> {
        var response = apolloClient.query(
            BalloonListQuery(
                after = Optional.presentIfNotNull(key),
                filter = Optional.presentIfNotNull(filter),
            )
        ).execute()

        // If the query fails due to buggy nature then re-initiate the query
        if (!response.errors.isNullOrEmpty()) {
            response = apolloClient.query(
                BalloonListQuery(
                    after = Optional.presentIfNotNull(key),
                    filter = Optional.presentIfNotNull(filter),
                )
            ).execute()
        }

        return response
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}