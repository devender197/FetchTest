package com.dev.fetchtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.fetchtest.repository.DataRepositoryInterface
import com.dev.fetchtest.repository.models.DataModel
import com.dev.fetchtest.ui.enums.UiState
import com.dev.fetchtest.ui.model.DataUiModel
import com.dev.fetchtest.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var mockRepository: DataRepositoryInterface

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mock()
        viewModel = MainViewModel(mockRepository)
    }

    @Test
    fun testLoadingOnDataFromRepository() = runTest {
        whenever(mockRepository.getData()).thenReturn(flow { emit(emptyList<DataModel>()) })

        viewModel.getDataFromRepository()

        assertEquals(UiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun testSuccessOnDataFromRepository() = runTest {
        val mockData = List(10) { // Or use repeat with MutableList
            DataModel(
                id = it,
                listId = it,
                name = "List id $it"
            )
        }
        whenever(mockRepository.getData()).thenReturn(flow { emit(mockData) })

        viewModel.getDataFromRepository()
        advanceUntilIdle() // Process all coroutine events

        val expectedData =  // Or use repeat with MutableList
            DataUiModel(
                backgroundColor = Utils.getRandomColor(),
                dataModelList = List(10) {
                    DataModel(
                        id = it,
                        listId = it,
                        name = "List id $it"
                    )
                }
            )

        assert(viewModel.uiState.value is UiState.Success<*>)
        assertEquals(
            expectedData.dataModelList.size,
            (viewModel.uiState.value as UiState.Success<List<DataUiModel>>).data.size
        )
    }

    @Test
    fun testErrorOnDataFromRepository() = runTest {
        whenever(mockRepository.getData()).thenReturn(flow { emit(null) })

        viewModel.getDataFromRepository()
        advanceUntilIdle() // Process all coroutine events

        assert(viewModel.uiState.value is UiState.Error)
        assertEquals(
            "Oops something has happened!!. Please check your internet connection",
            (viewModel.uiState.value as UiState.Error).message
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}