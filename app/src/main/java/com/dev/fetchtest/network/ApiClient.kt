package com.dev.fetchtest.network

import com.dev.fetchtest.network.base.APIClientError
import com.dev.fetchtest.network.base.ApiResponse
import com.dev.fetchtest.network.base.BaseApi
import com.dev.fetchtest.network.base.RequestModelInterface
import com.dev.fetchtest.network.base.RetrofitBuilder
import com.dev.fetchtest.network.extension.toApiResponse
import com.dev.fetchtest.network.model.request.DataRequest
import com.dev.fetchtest.network.model.response.DataResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiClient: ApiClientInterface {

    private var api: BaseApi = RetrofitBuilder.networkClient

    override fun getData() = call<DataResponse>(DataRequest())

    private inline fun <reified T> call(model: RequestModelInterface): Flow<ApiResponse<T>> =
        flow {
            emit(when (model) {
                is DataRequest -> api.getData(model.url()).toApiResponse<T>()

                else -> {
                    ApiResponse.Error(APIClientError.MissingModel())
                }
            }
            )
        }


}
