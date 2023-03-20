package com.example.jetpackcomposemedicines.domain

import com.example.jetpackcomposemedicines.data.MedicinesRepository
import com.example.jetpackcomposemedicines.data.model.Medicine
import javax.inject.Inject

class GetMedicinesUseCase @Inject constructor(private val medicinesRepository: MedicinesRepository) {

    suspend operator fun invoke(query: String): List<Medicine> {
        return medicinesRepository.getMedicines(query)
    }
}