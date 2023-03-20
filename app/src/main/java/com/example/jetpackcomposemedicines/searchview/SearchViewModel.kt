package com.example.jetpackcomposemedicines.searchview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.domain.GetMedicinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getMedicinesUseCase: GetMedicinesUseCase) :
    ViewModel() {

    val searchText = MutableStateFlow("")
    var matchedMedicines = MutableStateFlow<List<Medicine>>(emptyList())

    val searchModelState = combine(searchText, matchedMedicines) { text, matchedMedicines ->
        SearchModelState(
            text,
            matchedMedicines
        )
    }

    fun onSearchTextChanged(changedSearchText: String) {
        searchText.value = changedSearchText
        if (changedSearchText.isEmpty()) {
            matchedMedicines.value = emptyList()
        } else if (changedSearchText.length >= 3) {
            viewModelScope.launch {
                matchedMedicines.value = getMedicinesUseCase(changedSearchText)
            }
        }
    }

    fun onClearClick() {
        searchText.value = ""
        matchedMedicines.value = emptyList()
    }
}