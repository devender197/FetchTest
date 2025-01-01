package com.dev.fetchtest.network

import com.dev.fetchtest.network.base.ApiResponse
import com.dev.fetchtest.network.model.response.DataResponse
import kotlinx.coroutines.flow.Flow

interface ApiClientInterface {

    fun getData(): Flow<ApiResponse<DataResponse>>
}