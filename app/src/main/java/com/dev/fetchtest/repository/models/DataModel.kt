package com.dev.fetchtest.repository.models

import kotlinx.serialization.Serializable

@Serializable
data class DataModel(
    val id: Int?,
    val listId: Int?,
    val name: String?
)

