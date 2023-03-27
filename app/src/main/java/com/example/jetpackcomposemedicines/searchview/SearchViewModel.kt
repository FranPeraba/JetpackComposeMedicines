package com.example.jetpackcomposemedicines.searchview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.domain.GetMedicinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getMedicinesUseCase: GetMedicinesUseCase) :
    ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _matchedMedicines = MutableStateFlow<List<Medicine>>(emptyList())
    val matchedMedicines: StateFlow<List<Medicine>> = _matchedMedicines

    val searchModelState = combine(_searchText, _matchedMedicines) { text, matchedMedicines ->
        SearchModelState(
            text,
            matchedMedicines
        )
    }

    fun onSearchTextChanged(changedSearchText: String) {
        _searchText.value = changedSearchText
        if (changedSearchText.isEmpty()) {
            _matchedMedicines.value = emptyList()
        } else if (changedSearchText.length >= 3) {
            viewModelScope.launch {
                _matchedMedicines.value = getMedicinesUseCase(changedSearchText)
            }
        }
    }

    fun onClearClick() {
        _searchText.value = ""
        _matchedMedicines.value = emptyList()
    }
}