package com.example.jetpackcomposemedicines.searchview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposemedicines.data.model.MedicineModel
import com.example.jetpackcomposemedicines.domain.GetMedicinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getMedicinesUseCase: GetMedicinesUseCase): ViewModel() {

    private val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedMedicines: MutableStateFlow<List<MedicineModel>> = MutableStateFlow(arrayListOf())

    val medicineSearchModelState = combine(
        searchText,
        matchedMedicines,
        showProgressBar
    ) { text, matchedMedcicines, showProgress ->

        MedicineSearchModelState(text, matchedMedcicines, showProgress)
    }

    fun onSearchTextChanged(changedSearchText: String) {
        searchText.value = changedSearchText
        matchedMedicines.value = arrayListOf()
        if (changedSearchText.length >= 3){
            viewModelScope.launch {
                matchedMedicines.value = getMedicinesUseCase(changedSearchText)
            }
        } else {
            matchedMedicines.value = arrayListOf()
        }

    }

    fun onClearClick() {
        searchText.value = ""
        matchedMedicines.value = arrayListOf()
    }
}