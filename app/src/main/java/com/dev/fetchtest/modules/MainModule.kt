package com.dev.fetchtest.modules

import com.dev.fetchtest.network.ApiClient
import com.dev.fetchtest.network.ApiClientInterface
import com.dev.fetchtest.repository.DataRepository
import com.dev.fetchtest.repository.DataRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideApiClient(): ApiClientInterface = ApiClient()

    @Provides
    @Singleton
    fun provideDataRepository(apiClient: ApiClientInterface): DataRepositoryInterface =
        DataRepository(apiClient)
}