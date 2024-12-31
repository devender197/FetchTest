package com.dev.fetchtest.ui.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dev.fetchtest.viewmodels.MainViewModel

@Composable
fun MainView() {
    val viewModel: MainViewModel = hiltViewModel()
    val data by viewModel.response.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getDataFromRepository()
    }

    if (data.isEmpty()) {
        SplashView()
    } else {
        ListView(data = data)
    }
}

@Preview
@Composable
fun Preview_MainView() {
    MaterialTheme {
        MainView()
    }
}