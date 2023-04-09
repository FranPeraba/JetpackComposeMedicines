package com.example.jetpackcomposemedicines.data.model

import com.google.gson.annotations.SerializedName

data class MedicinesResponse(
    @SerializedName("totalFilas")
    val totalFilas: Int,
    @SerializedName("pagina")
    val pagina: Int,
    @SerializedName("tamanioPagina")
    val tamanioPagina: Int,
    @SerializedName("resultados")
    val results: List<Medicine>
) {
}