package com.example.jetpackcomposemedicines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.domain.GetMedicinesUseCase
import com.example.jetpackcomposemedicines.searchview.SearchViewModel
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
class SearchViewModelTest {

    @RelaxedMockK
    private lateinit var getMedicinesUseCase: GetMedicinesUseCase

    private lateinit var searchViewModel: SearchViewModel

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
    fun onSearchTextChange_getMedicinesUseCase_ifQueryIsGreaterOrEqualThanThreeCharacters() = runTest {
        // Given
        val query = "Medicine"
        val medicinesList = listOf(Medicine("001", "Medicine 1"), Medicine("002", "Medicine 2"))
        coEvery { getMedicinesUseCase(any()) } returns medicinesList

        // When
        searchViewModel.onSearchTextChanged(query)

        // Then
        assertEquals(searchViewModel.matchedMedicines.value, medicinesList)
    }

    @Test
    fun onSearchTextChange_ifQueryIsEmpty_matchedMedicinesIsEmpty() {
        // Given
        val query = ""

        // When
        searchViewModel.onSearchTextChanged(query)

        // Then
        assertEquals(searchViewModel.matchedMedicines.value, emptyList<Medicine>())
    }

    @Test
    fun onClearClick_searchText_and_matchedMedicinesAreEmpty() {
        // Given
        searchViewModel.searchText.value = "Medicine"
        searchViewModel.matchedMedicines.value = listOf(Medicine("001", "Medicine 1"), Medicine("002", "Medicine 2"))

        // When
        searchViewModel.onClearClick()

        // Then
        assertEquals(searchViewModel.searchText.value, "")
        assertEquals(searchViewModel.matchedMedicines.value, emptyList<Medicine>())
    }
}