package com.dev.fetchtest.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class DataResponseItem(
    val id: Int?,
    val listId: Int?,
    val name: String?
)