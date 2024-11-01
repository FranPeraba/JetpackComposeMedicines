package com.example.jetpackcomposemedicines

import kotlinx.serialization.Serializable

@Serializable
object SearchView

@Serializable
data class DetailView(val id: String)