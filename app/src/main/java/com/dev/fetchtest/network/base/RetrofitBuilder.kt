package com.dev.fetchtest.network.base

import com.moczul.ok2curl.CurlInterceptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com"
    private const val REGULAR_TIMEOUT_DEFAULT = 60L

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    private fun getClient() = OkHttpClient.Builder().run {
        readTimeout(REGULAR_TIMEOUT_DEFAULT, TimeUnit.SECONDS)
        writeTimeout(REGULAR_TIMEOUT_DEFAULT, TimeUnit.SECONDS)
        connectTimeout(REGULAR_TIMEOUT_DEFAULT, TimeUnit.SECONDS)
        addInterceptor(getCurlRequestInterceptor())
    }.build()

    val networkClient: BaseApi = getRetrofit().create(BaseApi::class.java)

    private fun getCurlRequestInterceptor(): CurlInterceptor =
        CurlInterceptor { curl -> Timber.d("Request: $curl") }

}
