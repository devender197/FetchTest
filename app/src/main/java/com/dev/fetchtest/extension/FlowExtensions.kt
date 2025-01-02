package com.dev.fetchtest.extension

import com.dev.fetchtest.network.base.APIClientError
import com.dev.fetchtest.network.base.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

inline fun <reified T> Response<*>.toApiResponse(): ApiResponse<T> = if (this.isSuccessful) {
    ApiResponse.Success(
        data = this.body() as T
    )
} else {
    ApiResponse.Error(APIClientError.InvalidUrl())
}
