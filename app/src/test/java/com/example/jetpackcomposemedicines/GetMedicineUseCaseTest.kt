package com.example.jetpackcomposemedicines

import com.example.jetpackcomposemedicines.data.MedicinesRepository
import com.example.jetpackcomposemedicines.data.model.Document
import com.example.jetpackcomposemedicines.data.model.MedicineResponse
import com.example.jetpackcomposemedicines.domain.GetMedicineUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetMedicineUseCaseTest{

    @RelaxedMockK
    private lateinit var medicinesRepository: MedicinesRepository

    private lateinit var getMedicineUseCase: GetMedicineUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getMedicineUseCase = GetMedicineUseCase(medicinesRepository)
    }

    @Test
    fun `getMedicine when request a medicine from repository`() = runBlocking{
        // Given
        val medicine = MedicineResponse("001", "Medicine 1", docs = listOf(Document(1, "url", "urlHtml")))
        coEvery { medicinesRepository.getMedicine(any()) } returns medicine

        // When
        val response = getMedicineUseCase("query")

        // Then
        coVerify(exactly = 1) { medicinesRepository.getMedicine(any()) }
        assertEquals(response, medicine)
    }
}