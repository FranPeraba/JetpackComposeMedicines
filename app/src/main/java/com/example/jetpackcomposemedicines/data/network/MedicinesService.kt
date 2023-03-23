package com.example.jetpackcomposemedicines.data.network

import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.data.model.MedicineResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MedicinesService @Inject constructor(private val api: MedicinesApiClient) {

    suspend fun getMedicines(query: String): List<Medicine> {
        return withContext(Dispatchers.IO){
            val response = api.getMedicines(query)
            response.body()?.resultados ?: emptyList()
        }
    }

    suspend fun getMedicine(query: String): MedicineResponse? {
        return withContext(Dispatchers.IO){
            val response = api.getMedicine(query)
            response.body()
        }
    }
}