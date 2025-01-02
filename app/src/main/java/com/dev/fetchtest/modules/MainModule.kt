package com.dev.fetchtest.modules

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.dev.fetchtest.cache.CacheRepository
import com.dev.fetchtest.cache.CacheRepositoryInterface
import com.dev.fetchtest.network.ApiClient
import com.dev.fetchtest.network.ApiClientInterface
import com.dev.fetchtest.repository.DataRepository
import com.dev.fetchtest.repository.DataRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

private val Context.cacheDataStore by preferencesDataStore(name = "fetchDataStore")

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideApiClient(): ApiClientInterface = ApiClient()

    @Provides
    @Singleton
    fun provideDataRepository(
        @ApplicationContext appContext: Context,
        apiClient: ApiClientInterface,
        cacheRepositoryInterface: CacheRepositoryInterface
    ): DataRepositoryInterface =
        DataRepository(
            context = appContext,
            apiClient = apiClient,
            cacheRepository = cacheRepositoryInterface
        )

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideCacheRepository(
        @ApplicationContext appContext: Context,
        json: Json
    ): CacheRepositoryInterface = CacheRepository(
        dataStore = appContext.cacheDataStore,
        json = json
    )
}