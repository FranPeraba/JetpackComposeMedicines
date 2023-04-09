package com.example.jetpackcomposemedicines.detailview

import com.example.jetpackcomposemedicines.data.model.MedicineResponse

data class DetailModelState(
    val medicine: MedicineResponse = MedicineResponse("", "", listOf()),
    val showProgressBar: Boolean = false,
    val showError:Boolean = false
) {
    companion object {
        val Empty = DetailModelState()
    }
}
