package com.dev.fetchtest.ui.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dev.fetchtest.ui.enums.UiState
import com.dev.fetchtest.ui.enums.isType
import com.dev.fetchtest.ui.model.DataUiModel
import com.dev.fetchtest.viewmodels.MainViewModel

@Composable
@Suppress("UNCHECKED_CAST")
fun MainView() {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getDataFromRepository()
    }

    when(val currentState = state) {
        is UiState.Loading -> {
            SplashView()
        }

        is UiState.Success<*> -> {
            if (currentState.isType<List<DataUiModel>>()) {
                ListView(data = currentState.data as List<DataUiModel>)
            }
        }

        is UiState.Error -> {
            ErrorView(
                message = currentState.message,
                onRetry = {
                    viewModel.getDataFromRepository()
                }
            )
        }
    }

}

@Preview
@Composable
fun Preview_MainView() {
    MaterialTheme {
        MainView()
    }
}