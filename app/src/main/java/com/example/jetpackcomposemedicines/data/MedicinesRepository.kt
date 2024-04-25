package com.example.jetpackcomposemedicines.data

import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.data.model.MedicineResponse
import com.example.jetpackcomposemedicines.data.network.MedicinesService
import javax.inject.Inject

class MedicinesRepository @Inject constructor(private val medicinesService: MedicinesService) {

    suspend fun getMedicines(query: String): List<Medicine>{
        return medicinesService.getMedicines(query)
    }

    suspend fun getMedicine(query: String): MedicineResponse {
        return medicinesService.getMedicine(query)
    }
}