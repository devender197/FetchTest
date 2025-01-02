package com.dev.fetchtest.repository

import android.content.Context
import com.dev.fetchtest.Utils
import com.dev.fetchtest.cache.CacheRepositoryInterface
import com.dev.fetchtest.network.ApiClientInterface
import com.dev.fetchtest.network.base.ApiResponse
import com.dev.fetchtest.network.model.response.DataResponse
import com.dev.fetchtest.repository.models.DataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val context: Context,
    private var apiClient: ApiClientInterface,
    private var cacheRepository: CacheRepositoryInterface
) : DataRepositoryInterface {
    override suspend fun getData(): Flow<List<DataModel>?> =
        if (Utils.isConnectedToInternet(context)) {
            handleApiResponse()
        } else {
            cacheRepository.getListData()
        }

    private fun handleApiResponse(): Flow<List<DataModel>?> = apiClient.getData().map {
        when (it) {
            is ApiResponse.Success -> {
                it.data?.let { safeData ->
                    dataResponseToDataListModel(safeData).also { data ->
                        cacheRepository.putListData(data)
                    }
                }
            }

            is ApiResponse.Error -> {
                null
            }
        }
    }

    private fun dataResponseToDataListModel(dataResponse: DataResponse): List<DataModel> =
        dataResponse.map { data ->
            DataModel(
                id = data.id,
                listId = data.listId,
                name = data.name
            )
        }
}