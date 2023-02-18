package com.example.jetpackcomposemedicines.data.network

import com.example.jetpackcomposemedicines.data.model.MedicineModel
import com.example.jetpackcomposemedicines.data.model.MedicinesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MedicinesService @Inject constructor(private val api: MedicinesApiClient) {

    suspend fun getMedicines(query: String): List<MedicineModel> {
        return withContext(Dispatchers.IO){
            val response = api.getMedicines(query)
            response.body()?.resultados ?: emptyList()
        }
    }
}