package com.example.jetpackcomposemedicines.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("tipo")
    val type: String,
    @SerializedName("url")
    val url: String
)
