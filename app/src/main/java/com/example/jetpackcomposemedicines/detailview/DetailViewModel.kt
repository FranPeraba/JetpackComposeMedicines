package com.example.jetpackcomposemedicines.detailview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposemedicines.domain.GetMedicineUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailViewModel.DetailViewModelFactory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted val medicineId: String,
    private val getMedicineUseCase: GetMedicineUseCase
    ): ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState
        .onStart { loadMedicine() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            DetailUiState()
        )

    private fun loadMedicine() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(showProgressBar = true)
            }
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        medicine = getMedicineUseCase(medicineId),
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
                Log.e(DetailViewModel::class.simpleName, "Unable to load medicine")
            }
        }
    }

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(medicineId: String) : DetailViewModel
    }
}