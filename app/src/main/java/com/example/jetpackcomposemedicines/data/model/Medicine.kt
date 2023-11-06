package com.example.jetpackcomposemedicines.data.model

import com.google.gson.annotations.SerializedName

data class Medicine(
    @SerializedName("nregistro")
    val id: String,
    @SerializedName("nombre")
    val name: String,
    @SerializedName("fotos")
    val photos: List<Foto>? = listOf()
)