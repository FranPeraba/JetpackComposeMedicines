package com.example.jetpackcomposemedicines.domain

import com.example.jetpackcomposemedicines.data.MedicinesRepository
import com.example.jetpackcomposemedicines.data.model.MedicineResponse
import javax.inject.Inject

class GetMedicineUseCase @Inject constructor(private val medicinesRepository: MedicinesRepository) {

    suspend operator fun invoke(query: String): MedicineResponse {
        return medicinesRepository.getMedicine(query)
    }
}