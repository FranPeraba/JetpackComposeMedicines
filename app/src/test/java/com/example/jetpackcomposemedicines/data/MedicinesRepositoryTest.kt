package com.example.jetpackcomposemedicines.data

import com.example.jetpackcomposemedicines.data.model.Document
import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.data.model.MedicineResponse
import com.example.jetpackcomposemedicines.data.network.MedicinesService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MedicinesRepositoryTest {

    @RelaxedMockK
    private lateinit var medicinesService: MedicinesService

    private lateinit var medicinesRepository: MedicinesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        medicinesRepository = MedicinesRepository(medicinesService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `getMedicines when requests medicines from api`() = runTest  {
        // Given
        val medicine1 = Medicine("001", "Medicine 1")
        val medicine2 = Medicine("002", "Medicine 2")
        val medicinesList = listOf(medicine1, medicine2)
        coEvery { medicinesService.getMedicines(any()) } returns medicinesList

        // When
        val response = medicinesRepository.getMedicines("query")

        // Then
        coVerify(exactly = 1) { medicinesService.getMedicines(any()) }
        assertEquals(response, medicinesList)
    }

    @Test
    fun `getMedicine when request a medicine from api`() = runTest {
        // Given
        val medicine = MedicineResponse("001", "Medicine 1", docs = listOf(Document(1, "url", "urlHtml")))
        coEvery { medicinesService.getMedicine(any()) } returns medicine

        // When
        val response = medicinesRepository.getMedicine("query")

        // Then
        coVerify(exactly = 1) { medicinesService.getMedicine(any()) }
        assertEquals(response, medicine)
    }
}