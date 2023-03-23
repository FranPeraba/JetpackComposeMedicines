package com.example.jetpackcomposemedicines.data.model

import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("tipo")
    val tipo: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlHtml")
    val urlHtml: String
)
