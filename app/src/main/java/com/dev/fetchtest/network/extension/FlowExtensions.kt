package com.dev.fetchtest.network.extension

import com.dev.fetchtest.network.base.APIClientError
import com.dev.fetchtest.network.base.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

inline fun <T> emitFlow(crossinline block: suspend () -> T): Flow<T> =
    flow { emit(block()) }.flowOn(Dispatchers.IO)

inline fun <reified T> Response<*>.toApiResponse(): ApiResponse<T> = if (this.isSuccessful) {
    ApiResponse.Success(
        data = this.body() as T
    )
} else {
    ApiResponse.Error(APIClientError.InvalidUrl())
}

inline fun <T, E : Throwable> Flow<T>.mapError(crossinline block: suspend (Throwable) -> E) =
    catch { throw block(it) }
