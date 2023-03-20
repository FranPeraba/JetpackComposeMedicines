package com.example.jetpackcomposemedicines.data

import android.util.Log
import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.data.network.MedicinesService
import javax.inject.Inject

class MedicinesRepository @Inject constructor(private val medicinesService: MedicinesService) {

    private var medicines: List<Medicine> = arrayListOf()

    suspend fun getMedicines(query: String): List<Medicine>{
        try {
            medicines = medicinesService.getMedicines(query)
        } catch (ex: Exception) {
            Log.e(MedicinesRepository::class.simpleName, "Unable to get medicines")
        }
        return medicines
    }
}