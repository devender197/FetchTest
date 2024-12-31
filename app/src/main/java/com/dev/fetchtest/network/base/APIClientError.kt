package com.dev.fetchtest.network.base

import kotlinx.serialization.Serializable

@Serializable
sealed class APIClientError(
    open val errorMessage: String,
    open val errorCode: Int? = null
) : Exception(errorMessage) {
    class InvalidUrl : APIClientError("Malformed Request Url")
    class InvalidNamespace : APIClientError("Malformed namespace")
    class InvalidConfigSuffix : APIClientError("Malformed Config Suffix")
    class MissingAuthToken : APIClientError("Authorization is missing")
    class MissingModel : APIClientError("one of the model value is missing")

}