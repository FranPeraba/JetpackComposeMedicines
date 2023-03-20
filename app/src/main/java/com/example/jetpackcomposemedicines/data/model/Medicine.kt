package com.example.jetpackcomposemedicines.data.model

import com.google.gson.annotations.SerializedName

data class Medicine(
    @SerializedName("nregistro")
    val nregistro: String,
    @SerializedName("nombre")
    val nombre: String
) {
}