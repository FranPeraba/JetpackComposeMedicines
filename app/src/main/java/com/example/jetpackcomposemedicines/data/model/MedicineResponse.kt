package com.example.jetpackcomposemedicines.data.model

import com.google.gson.annotations.SerializedName

data class MedicineResponse(
    @SerializedName("nregistro")
    val nregistro: String,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("docs")
    val docs: List<Document>
)
