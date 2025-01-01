package com.dev.fetchtest.repository

import com.dev.fetchtest.network.ApiClientInterface
import com.dev.fetchtest.network.base.ApiResponse
import com.dev.fetchtest.network.model.response.DataResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepository @Inject constructor(
    private var apiClient: ApiClientInterface
) : DataRepositoryInterface {
    override suspend fun getData(): Flow<ApiResponse<DataResponse>> = apiClient.getData()
}