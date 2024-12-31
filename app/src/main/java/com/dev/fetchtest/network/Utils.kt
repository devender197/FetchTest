package com.dev.fetchtest.network

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

object Utils {

    fun getRandomColor(): Color {
        val max = 0.5f // Upper limit for RGB components to ensure dark colors
        return Color(
            red = Random.nextFloat() * max,
            green = Random.nextFloat() * max,
            blue = Random.nextFloat() * max,
            alpha = 1.0f // Fully opaque
        )
    }

}