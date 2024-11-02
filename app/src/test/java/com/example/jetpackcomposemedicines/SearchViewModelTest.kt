package com.example.jetpackcomposemedicines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.domain.GetMedicinesUseCase
import com.example.jetpackcomposemedicines.searchview.SearchViewModel
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
class SearchViewModelTest {

    @RelaxedMockK
    private lateinit var getMedicinesUseCase: GetMedicinesUseCase

    private lateinit var searchViewModel: SearchViewModel
    private val queryLength = 4

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        searchViewModel = SearchViewModel(getMedicinesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSearchTextChange getMedicinesUseCase if query length is greater or equal than min query length`() = runTest {
        // Given
        val query = "Medicine"
        val expectedMedicinesList = listOf(Medicine("001", "Medicine 1"), Medicine("002", "Medicine 2"))
        coEvery { getMedicinesUseCase(any()) } returns expectedMedicinesList

        // When
        searchViewModel.onSearchTextChanged(query, queryLength)

        // Then
        coVerify(exactly = 1) { getMedicinesUseCase(any()) }
        assertEquals(expectedMedicinesList, searchViewModel.matchedMedicines.value)
    }

    @Test
    fun `onSearchTextChange if query is empty matchedMedicines is empty`() {
        // Given
        val query = ""

        // When
        searchViewModel.onSearchTextChanged(query, queryLength)

        // Then
        coVerify(exactly = 0) { getMedicinesUseCase(any()) }
        assertEquals(emptyList<Medicine>(), searchViewModel.matchedMedicines.value)
    }

    @Test
    fun `onClearClick searchText and matchedMedicines are empty`() = runTest {
        // Given
        val query = "Medicine"
        val medicinesList = listOf(Medicine("001", "Medicine 1"), Medicine("002", "Medicine 2"))
        coEvery { getMedicinesUseCase(any()) } returns medicinesList

        // When
        searchViewModel.onSearchTextChanged(query, queryLength)
        searchViewModel.onClearClick()

        // Then
        coVerify(exactly = 1) { getMedicinesUseCase(any()) }
        assertEquals("", searchViewModel.searchText.value)
        assertEquals(emptyList<Medicine>(), searchViewModel.matchedMedicines.value)
    }
}