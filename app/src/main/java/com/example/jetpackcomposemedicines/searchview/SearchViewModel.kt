package com.example.jetpackcomposemedicines.searchview

import android.util.Log
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

    fun onSearchTextChanged(changedSearchText: String, minQueryLength: Int) {
        _searchText.value = changedSearchText
        if (changedSearchText.isEmpty()) {
            _matchedMedicines.value = emptyList()
            return
        }
        if (changedSearchText.length >= minQueryLength) {
            viewModelScope.launch {
                try {
                    _matchedMedicines.value = getMedicinesUseCase(changedSearchText)
                } catch (networkError: Exception) {
                    Log.e(SearchViewModel::class.simpleName, "Unable to get medicines")
                }
            }
        }
    }

    fun onClearClick() {
        _searchText.value = ""
        _matchedMedicines.value = emptyList()
    }
}