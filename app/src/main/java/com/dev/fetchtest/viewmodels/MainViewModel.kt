package com.dev.fetchtest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.fetchtest.network.Utils
import com.dev.fetchtest.network.base.ApiResponse
import com.dev.fetchtest.network.model.response.DataResponse
import com.dev.fetchtest.network.model.response.DataResponseItem
import com.dev.fetchtest.repository.NetworkRepository
import com.dev.fetchtest.ui.models.DataUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    companion object {
        private const val SPLASH_LOADING_DURATION_MS = 3_000L
    }

    var response: MutableStateFlow<List<DataUIModel>> = MutableStateFlow(emptyList())

    fun getDataFromRepository() {
        viewModelScope.launch {
            networkRepository.getData().collect { apiResponse ->
                handleApiResponse(apiResponse)
            }
        }
    }

    private suspend fun handleApiResponse(apiResponse: ApiResponse<DataResponse>) {
        when (apiResponse) {
            is ApiResponse.Error -> handleError(apiResponse)
            is ApiResponse.Success -> handleSuccess(apiResponse.data)
        }
    }

    private suspend fun handleSuccess(data: List<DataResponseItem>) {
        // Delay to display the splash screen for a fixed duration
        delay(SPLASH_LOADING_DURATION_MS)

        response.value = data
            .filterNot { it.name.isNullOrBlank() } // filter null or blank names
            .sortedBy { it.listId } // Sort by listId
            .groupBy { it.listId } // Group by listId
            .map { groupedItem -> // Transform into DataUIModel
                DataUIModel(
                    backgroundColor = Utils.getRandomColor(),
                    dataResponseItems = groupedItem.value
                        .sortedBy { item ->
                            item.name?.filter { it.isDigit() }?.toIntOrNull() ?: Int.MAX_VALUE
                        }
                )
            }
    }

    private fun handleError(error: ApiResponse<DataResponse>) {
        Timber.e("Error $error")
    }
}
