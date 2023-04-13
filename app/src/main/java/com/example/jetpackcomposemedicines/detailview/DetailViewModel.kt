package com.example.jetpackcomposemedicines.detailview

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposemedicines.domain.GetMedicineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle,
    private val getMedicineUseCase: GetMedicineUseCase
    ): ViewModel() {

    private val medicineId = savedStateHandle.get<String>("id")

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        getMedicine()
    }

    private fun getMedicine() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(showProgressBar = true)
            }
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        medicine = getMedicineUseCase(medicineId!!)!!,
                        showProgressBar = false
                    )
                }
            } catch (networkError: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        showError = true,
                        showProgressBar = false
                    )
                }
                Log.e(DetailViewModel::class.simpleName, "Unable to get medicine")
            }
        }
    }
}