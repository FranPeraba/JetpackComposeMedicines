package com.example.jetpackcomposemedicines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.jetpackcomposemedicines.data.model.Document
import com.example.jetpackcomposemedicines.data.model.MedicineResponse
import com.example.jetpackcomposemedicines.detailview.DetailViewModel
import com.example.jetpackcomposemedicines.domain.GetMedicineUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
    fun `load medicine when start flow in viewModel`() = runTest {
        // Given
        val expectedMedicine = MedicineResponse("53789", "Medicine name", listOf(Document(1, "A url", "A urlHtml")))
        coEvery { getMedicineUseCase(any()) } returns expectedMedicine

        // When
        val underTest = DetailViewModel("53789", getMedicineUseCase)

        // Then
        underTest.uiState.test {
            val item = awaitItem()
            coVerify(exactly = 1) { getMedicineUseCase(any()) }
            assertEquals(item.medicine, underTest.uiState.value.medicine)
        }
    }
}