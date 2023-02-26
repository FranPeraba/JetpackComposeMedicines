package com.example.jetpackcomposemedicines.searchview

import com.example.jetpackcomposemedicines.data.model.MedicineModel

data class MedicineSearchModelState(
    val searchText: String = "",
    val medicines: List<MedicineModel> = arrayListOf(),
    val showProgressBar: Boolean = false
) {
    companion object {
        val Empty = MedicineSearchModelState()
    }
}