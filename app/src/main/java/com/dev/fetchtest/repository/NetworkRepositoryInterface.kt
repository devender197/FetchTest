package com.dev.fetchtest.repository

import com.dev.fetchtest.network.base.ApiResponse
import com.dev.fetchtest.network.model.response.DataResponse
import kotlinx.coroutines.flow.Flow

interface NetworkRepositoryInterface {
    suspend fun getData(): Flow<ApiResponse<DataResponse>>
}