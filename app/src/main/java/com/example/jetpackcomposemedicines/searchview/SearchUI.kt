package com.example.jetpackcomposemedicines.searchview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.example.jetpackcomposemedicines.NavRoutes
import com.example.jetpackcomposemedicines.components.SearchBarUI
import com.example.jetpackcomposemedicines.data.model.Medicine

@ExperimentalComposeUiApi
@Composable
fun SearchUI(navHostController: NavHostController, searchViewModel: SearchViewModel) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val keyboardController = LocalSoftwareKeyboardController.current

    val searchModelState by produceState(
        initialValue = SearchModelState.Empty,
        key1 = lifecycle,
        key2 = searchViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            searchViewModel.searchModelState.collect { value = it }
        }
    }
    SearchBarUI(
        searchText = searchModelState.searchText,
        placeholderText = "Busca tus medicamentos aquí",
        onSearchTextChanged = { searchViewModel.onSearchTextChanged(it) },
        onClearClick = { searchViewModel.onClearClick() },
        matchesFound = searchModelState.medicines.isNotEmpty()
    ) {
        Medicines(medicines = searchModelState.medicines) { medicine ->
            navHostController.navigate(route = "${NavRoutes.Detail.route}?id=${medicine.id}")
            keyboardController?.hide()
        }
    }
}

@Composable
fun Medicines(medicines: List<Medicine>?, onClick: (Medicine) -> Unit) {
    medicines?.forEach { medicine ->
        MedicineRow(medicine = medicine) {
            onClick(medicine)
        }
        Divider()
    }
}

@Composable
fun MedicineRow(medicine: Medicine, onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }) {
        Text(text = medicine.name, fontSize = 14.sp, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(2.dp))
    }
}