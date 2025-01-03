package com.dev.fetchtest.repository

import com.dev.fetchtest.network.base.ApiResponse
import com.dev.fetchtest.network.model.response.DataResponse
import com.dev.fetchtest.repository.models.DataModel
import kotlinx.coroutines.flow.Flow

interface DataRepositoryInterface {
    suspend fun getData(): Flow<List<DataModel>?>
}