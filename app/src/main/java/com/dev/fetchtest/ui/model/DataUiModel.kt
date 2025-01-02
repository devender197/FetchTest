package com.dev.fetchtest.ui.model

import androidx.compose.ui.graphics.Color
import com.dev.fetchtest.repository.models.DataModel

data class DataUiModel(
    val backgroundColor: Color,
    val dataModelList: List<DataModel>
)