package com.example.jetpackcomposemedicines.data

import com.example.jetpackcomposemedicines.data.model.MedicineModel
import com.example.jetpackcomposemedicines.data.network.MedicinesService
import javax.inject.Inject

class MedicinesRepository @Inject constructor(private val medicinesService: MedicinesService) {

    suspend fun getMedicines(query: String): List<MedicineModel>{
        return medicinesService.getMedicines(query)
    }
}