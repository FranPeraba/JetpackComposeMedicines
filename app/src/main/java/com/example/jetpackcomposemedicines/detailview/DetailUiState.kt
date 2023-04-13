package com.example.jetpackcomposemedicines.detailview

import com.example.jetpackcomposemedicines.data.model.MedicineResponse

data class DetailUiState(
    val medicine: MedicineResponse = MedicineResponse(),
    val showProgressBar: Boolean = false,
    val showError:Boolean = false
)
