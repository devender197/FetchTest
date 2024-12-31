package com.dev.fetchtest.network.base

import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse<T> {
    @Serializable
    data class Success<T>(val data: T) : ApiResponse<T>()

    @Serializable
    data class Error<T>(val error: APIClientError) : ApiResponse<T>()
}