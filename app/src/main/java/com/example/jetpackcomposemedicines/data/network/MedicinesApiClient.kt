package com.example.jetpackcomposemedicines.data.network

import com.example.jetpackcomposemedicines.data.model.MedicineResponse
import com.example.jetpackcomposemedicines.data.model.MedicinesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MedicinesApiClient {

    @GET("medicamentos")
    suspend fun getMedicines(@Query("multiple") query: String): Response<MedicinesResponse>

    @GET("medicamento")
    suspend fun getMedicine(@Query("nregistro") query: String): Response<MedicineResponse>
}