package com.dev.fetchtest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.fetchtest.Utils
import com.dev.fetchtest.repository.DataRepositoryInterface
import com.dev.fetchtest.repository.models.DataModel
import com.dev.fetchtest.ui.enums.UiState
import com.dev.fetchtest.ui.model.DataUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRepository: DataRepositoryInterface
) : ViewModel() {

    companion object {
        private const val SPLASH_LOADING_DURATION_MS = 3_000L
    }

    val uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)

    fun getDataFromRepository() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            networkRepository.getData().collect { data ->
                handleDataResponse(data)
            }
        }
    }

    private suspend fun handleDataResponse(dataList: List<DataModel>?) {
        dataList?.let { data ->
            // Delay to display the splash screen for a fixed duration
            delay(SPLASH_LOADING_DURATION_MS)
            data.filterNot { it.name.isNullOrBlank() }
                .sortedBy { it.listId }
                .groupBy { it.listId }
                .map { groupedItem ->
                    DataUiModel(
                        backgroundColor = Utils.getRandomColor(),
                        dataModelList = groupedItem.value
                            .sortedBy { item ->
                                item.name?.filter { it.isDigit() }?.toIntOrNull() ?: Int.MAX_VALUE
                            }
                    )
                }.also {
                    // update the ui state to success
                    uiState.value = UiState.Success(it)
                }
        } ?: handleError()
    }

    private fun handleError() {
        val errorMessage = "Oops something has happened!!. Please check your internet connection"
        uiState.value = UiState.Error(errorMessage)
        Timber.e(errorMessage)
    }
}
