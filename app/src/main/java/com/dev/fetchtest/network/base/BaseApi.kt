package com.dev.fetchtest.network.base

import com.dev.fetchtest.network.model.response.DataResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface BaseApi {
    @GET
    suspend fun getData(
        @Url url: String,
    ): Response<DataResponse>
}