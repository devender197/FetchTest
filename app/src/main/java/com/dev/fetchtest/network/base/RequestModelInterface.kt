package com.dev.fetchtest.network.base

interface RequestModelInterface {
    fun path(): String? = null
    fun headers(): Map<String, String> = emptyMap()
    fun queryParams(): Map<String, String> = emptyMap()
    fun url(): String = path() ?: throw APIClientError.InvalidUrl()
}