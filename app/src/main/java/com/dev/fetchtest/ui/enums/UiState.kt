package com.dev.fetchtest.ui.enums

sealed class UiState {
    data object Loading : UiState()

    data class Error(val message: String) : UiState()

    class Success<T>(val data: T) : UiState()

}

inline fun <reified T> UiState.Success<*>.isType(): Boolean {
    return data is T
}
