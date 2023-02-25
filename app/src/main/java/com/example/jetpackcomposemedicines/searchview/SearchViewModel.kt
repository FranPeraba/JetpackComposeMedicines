package com.example.jetpackcomposemedicines.searchview

import androidx.lifecycle.ViewModel
import com.example.jetpackcomposemedicines.domain.GetMedicinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getMedicinesUseCase: GetMedicinesUseCase): ViewModel() {
}