package com.dev.fetchtest.repository

import com.dev.fetchtest.network.ApiClient
import com.dev.fetchtest.network.base.ApiResponse
import com.dev.fetchtest.network.model.response.DataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private var apiClient: ApiClient
) : NetworkRepositoryInterface {

    override suspend fun getData(): Flow<ApiResponse<DataResponse>> = apiClient.getData()
}