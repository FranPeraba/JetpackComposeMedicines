package com.example.jetpackcomposemedicines

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.jetpackcomposemedicines.data.model.Document
import com.example.jetpackcomposemedicines.data.model.MedicineResponse
import com.example.jetpackcomposemedicines.detailview.DetailViewModel
import com.example.jetpackcomposemedicines.domain.GetMedicineUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
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
        val medicine = MedicineResponse("53789", "Medicine name", listOf(Document(1, "A url", "A urlHtml")))
        coEvery { getMedicineUseCase(any()) } returns medicine

        val savedStateHandle = SavedStateHandle().apply {
            set("id", "53789")
        }
        val context = mockk<Context>(relaxed = true)

        // When
        detailViewModel = DetailViewModel(savedStateHandle, getMedicineUseCase, context)

        // Then
        assertEquals(detailViewModel.medicine.value, medicine)
    }
}