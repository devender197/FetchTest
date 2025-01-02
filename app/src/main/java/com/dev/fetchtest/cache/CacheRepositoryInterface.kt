package com.dev.fetchtest.cache

import com.dev.fetchtest.repository.models.DataModel
import kotlinx.coroutines.flow.Flow

interface CacheRepositoryInterface {
    fun getListData(): Flow<List<DataModel>?>

    suspend fun putListData(value: List<DataModel>)
}