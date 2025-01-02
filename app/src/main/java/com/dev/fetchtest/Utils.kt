package com.dev.fetchtest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

}