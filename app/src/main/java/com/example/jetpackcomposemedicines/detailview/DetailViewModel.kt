package com.example.jetpackcomposemedicines.detailview

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposemedicines.data.model.MedicineResponse
import com.example.jetpackcomposemedicines.domain.GetMedicineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle,
    private val getMedicineUseCase: GetMedicineUseCase
    ): ViewModel() {

    private val medicineId = savedStateHandle.get<String>("id")

    private val _medicine = MutableStateFlow(MedicineResponse())
    val medicine: StateFlow<MedicineResponse> = _medicine

    private var _showProgressBar = MutableStateFlow(false)

    private var _showNetworkError = MutableStateFlow(false)

    val detailModelState = combine(
        _medicine,
        _showProgressBar,
        _showNetworkError
    ) { medicine, showProgressBar, showNetworkError ->
        DetailModelState(
            medicine,
            showProgressBar,
            showNetworkError
        )
    }

    init {
        getMedicine()
    }

    private fun getMedicine() {
        viewModelScope.launch {
            _showProgressBar.value = true
            try {
                _medicine.value = getMedicineUseCase(medicineId!!)!!
                _showProgressBar.value = false
            } catch (networkError: Exception) {
                _showNetworkError.value = true
                _showProgressBar.value = false
                Log.e(DetailViewModel::class.simpleName, "Unable to get medicine")
            }
        }
    }
}