package com.example.jetpackcomposemedicines.searchview

import com.example.jetpackcomposemedicines.data.model.Medicine

data class SearchModelState(
    val searchText: String = "",
    val medicines: List<Medicine> = arrayListOf()
) {
    companion object {
        val Empty = SearchModelState()
    }
}