package com.example.jetpackcomposemedicines.searchview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.jetpackcomposemedicines.R
import com.example.jetpackcomposemedicines.components.SearchBarUI
import com.example.jetpackcomposemedicines.data.model.Medicine
import com.example.jetpackcomposemedicines.ui.theme.JetpackComposeMedicinesTheme

@ExperimentalComposeUiApi
@Composable
fun SearchUI(
    onMedicineClicked: (Medicine) -> Unit = {},
    searchViewModel: SearchViewModel = viewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val keyboardController = LocalSoftwareKeyboardController.current
    val minQueryLength = 4

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
        onSearchTextChanged = { searchViewModel.onSearchTextChanged(it, minQueryLength) },
        onClearClick = { searchViewModel.onClearClick() },
        matchesFound = searchModelState.medicines.isNotEmpty(),
        minQueryLength = minQueryLength
    ) {
        Medicines(medicines = searchModelState.medicines) { medicine ->
            onMedicineClicked(medicine)
            keyboardController?.hide()
        }
    }
}

@Composable
fun Medicines(
    medicines: List<Medicine>?,
    onClick: (Medicine) -> Unit
) {
    medicines?.forEach { medicine ->
        MedicineRow(medicine = medicine) {
            onClick(medicine)
        }
        Divider()
    }
}

@Composable
fun MedicineRow(
    medicine: Medicine,
    onClick: () -> Unit = {}
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = medicine.photos?.get(0)?.url,
            modifier = Modifier
                .size(75.dp)
                .align(Alignment.CenterVertically)
                .padding(PaddingValues(end = 6.dp)),
            contentScale = ContentScale.Fit,
            fallback = painterResource(R.drawable.no_disponible),
            contentDescription = null)
        Text(
            text = medicine.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically))
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
        //MedicineRow(Medicine("", "DIAZEPAM AUROVITAS SPAIN 5 MG COMPRIMIDOS EFG"))
    }

}