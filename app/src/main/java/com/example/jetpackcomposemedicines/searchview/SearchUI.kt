package com.example.jetpackcomposemedicines.searchview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.example.jetpackcomposemedicines.NavRoutes
import com.example.jetpackcomposemedicines.R
import com.example.jetpackcomposemedicines.components.SearchBarUI
import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.ui.theme.JetpackComposeMedicinesTheme

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
        placeholderText = stringResource(R.string.place_holder_search_bar),
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
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }) {
        Text(
            text = medicine.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_navigate_next),
            contentDescription = stringResource(R.string.next),
            modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    JetpackComposeMedicinesTheme {
        MedicineRow(Medicine("", "DIAZEPAM AUROVITAS SPAIN 5 MG COMPRIMIDOS EFG")){}
    }

}