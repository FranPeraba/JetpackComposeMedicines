package com.example.jetpackcomposemedicines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.jetpackcomposemedicines.data.model.Document
import com.example.jetpackcomposemedicines.data.model.MedicineResponse
import com.example.jetpackcomposemedicines.detailview.DetailViewModel
import com.example.jetpackcomposemedicines.domain.GetMedicineUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @RelaxedMockK
    private lateinit var getMedicineUseCase: GetMedicineUseCase

    private lateinit var detailViewModel: DetailViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMedicine when initialize viewModel`() = runTest {
        // Given
        val expectedMedicine = MedicineResponse("53789", "Medicine name", listOf(Document(1, "A url", "A urlHtml")))
        coEvery { getMedicineUseCase(any()) } returns expectedMedicine

        val savedStateHandle = SavedStateHandle().apply {
            set("id", "53789")
        }

        // When
        detailViewModel = DetailViewModel(savedStateHandle, getMedicineUseCase)

        // Then
        assertEquals(expectedMedicine, detailViewModel.uiState.value.medicine)
    }
}