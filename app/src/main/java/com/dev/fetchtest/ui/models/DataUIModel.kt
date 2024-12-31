package com.dev.fetchtest.ui.models

import androidx.compose.ui.graphics.Color
import com.dev.fetchtest.network.model.response.DataResponseItem

data class DataUIModel (
    val backgroundColor: Color,
    val dataResponseItems: List<DataResponseItem>
)

