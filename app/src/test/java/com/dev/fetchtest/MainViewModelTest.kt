package com.dev.fetchtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.fetchtest.network.base.APIClientError
import com.dev.fetchtest.network.base.ApiResponse
import com.dev.fetchtest.network.model.response.DataResponse
import com.dev.fetchtest.network.model.response.DataResponseItem
import com.dev.fetchtest.repository.DataRepositoryInterface
import com.dev.fetchtest.repository.models.DataModel
import com.dev.fetchtest.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.whenever
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var networkRepository: DataRepositoryInterface
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        networkRepository = mock()
        viewModel = MainViewModel(networkRepository)
    }

    @Test
    fun testSuccessOnDataFromRepository() = runTest {
        val mockData = DataResponse().apply {
            addAll(
                arrayListOf(
                    DataResponseItem(1, 1, "Test 1"),
                    DataResponseItem(2, 2, "Test 2"),
                    DataResponseItem(3, 1, "Another Test")
                )
            )
        }

        val apiResponse = ApiResponse.Success(mockData)

        whenever(networkRepository.getData()).thenReturn(flow { emit(apiResponse) })

        viewModel.getDataFromRepository()

        advanceUntilIdle()

        val expected = listOf(
            DataModel(
                backgroundColor = Utils.getRandomColor(),
                dataResponseItems = listOf(
                    DataResponseItem(1, 1, "Test 1"),
                    DataResponseItem(3, 1, "Another Test")
                )
            ),
            DataModel(
                backgroundColor = Utils.getRandomColor(),
                dataResponseItems = listOf(DataResponseItem(2, 2, "Test 2"))
            )
        )
        assertEquals(expected.size, viewModel.data.value.size)
    }

    @Test
    fun testErrorOnDataFromRepository() = runTest {
        val apiResponse = ApiResponse.Error<DataResponse>(APIClientError.MissingModel())

        whenever(networkRepository.getData()).thenReturn(flow { emit(apiResponse) })

        viewModel.getDataFromRepository()

        advanceUntilIdle()

        assertEquals(emptyList<DataModel>(), viewModel.data.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
