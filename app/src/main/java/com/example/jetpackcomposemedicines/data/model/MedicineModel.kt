package com.example.jetpackcomposemedicines.data.model

import com.google.gson.annotations.SerializedName

data class MedicineModel(
    @SerializedName("nregistro")
    val nregistro: String,
    @SerializedName("nombre")
    val nombre: String
) {
}