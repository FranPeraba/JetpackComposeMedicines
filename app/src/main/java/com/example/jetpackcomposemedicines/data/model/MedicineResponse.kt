package com.example.jetpackcomposemedicines.data.model

import com.google.gson.annotations.SerializedName

data class MedicineResponse(
    @SerializedName("nregistro")
    val id: String = "",
    @SerializedName("nombre")
    val name: String = "",
    @SerializedName("docs")
    val docs: List<Document> = listOf(),
    @SerializedName("fotos")
    val photos: List<Photo> = listOf()
)
