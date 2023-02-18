package com.example.jetpackcomposemedicines.domain

import com.example.jetpackcomposemedicines.data.MedicinesRepository
import com.example.jetpackcomposemedicines.data.model.MedicineModel
import javax.inject.Inject

class GetMedicinesUseCase @Inject constructor(private val medicinesRepository: MedicinesRepository) {

    suspend operator fun invoke(query: String): List<MedicineModel>{
        return medicinesRepository.getMedicines(query)
    }
}