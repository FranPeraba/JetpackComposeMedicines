package com.example.jetpackcomposemedicines.data.model

import com.google.gson.annotations.SerializedName

data class MedicinesResponse(
    @SerializedName("totalFilas")
    val totalRows: Int,
    @SerializedName("pagina")
    val page: Int,
    @SerializedName("tamanioPagina")
    val pageSize: Int,
    @SerializedName("resultados")
    val results: List<Medicine>
)