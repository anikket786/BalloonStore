package com.app.general.balloonstore.data.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.app.general.balloonstore.data.local.BalloonStoreDb
import com.app.general.balloonstore.data.local.dao.FavoriteBalloonsDao
import com.app.general.balloonstore.data.remote.EndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(EndPoint.GRAPH_QL_URL)
            .addHttpInterceptor(LoggingInterceptor(level = LoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideBalloonStoreDb(@ApplicationContext appContext: Context): BalloonStoreDb {
        return Room.databaseBuilder(
            appContext,
            BalloonStoreDb::class.java,
            BalloonStoreDb.DB_NAME,
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideFavoriteBalloonsDao(database: BalloonStoreDb): FavoriteBalloonsDao {
        return database.favoriteBalloonsDao
    }
}