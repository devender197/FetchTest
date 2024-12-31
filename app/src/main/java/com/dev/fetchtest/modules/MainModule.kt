package com.dev.fetchtest.modules

import com.dev.fetchtest.network.ApiClient
import com.dev.fetchtest.repository.NetworkRepository
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
    fun provideApiClient() = ApiClient()

    @Provides
    @Singleton
    fun provideNetworkRepository(apiClient: ApiClient)= NetworkRepository(apiClient)
}