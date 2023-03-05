package com.example.jetpackcomposemedicines.searchview

import com.example.jetpackcomposemedicines.data.model.MedicineModel

data class SearchModelState(
    val searchText: String = "",
    val medicines: List<MedicineModel> = arrayListOf()
) {
    companion object {
        val Empty = SearchModelState()
    }
}