package com.example.jetpackcomposemedicines

import com.example.jetpackcomposemedicines.data.MedicinesRepository
import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.domain.GetMedicinesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*


class GetMedicinesUseCaseTest {

    private val medicine1 = Medicine("001", "Medicine 1")
    private val medicine2 = Medicine("002", "Medicine 2")
    private val medicinesList = listOf(medicine1, medicine2)

    @RelaxedMockK
    private lateinit var medicinesRepository: MedicinesRepository

    private lateinit var getMedicinesUseCase: GetMedicinesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getMedicinesUseCase = GetMedicinesUseCase(medicinesRepository)
    }

    @Test
    fun `getMedicines when requests medicines from repository`() = runBlocking {
        // Given
        coEvery { medicinesRepository.getMedicines(any()) } returns medicinesList

        // When
        val response = getMedicinesUseCase(query = "query")

        // Then
        coVerify(exactly = 1) { medicinesRepository.getMedicines(any()) }
        assertEquals(response, medicinesList)
    }
}