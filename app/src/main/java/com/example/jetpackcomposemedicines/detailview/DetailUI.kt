package com.example.jetpackcomposemedicines.detailview

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController

@Composable
fun DetailUI(navHostController: NavHostController, detailViewModel: DetailViewModel) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val detailModelState by produceState(
        initialValue = DetailModelState.Empty,
        key1 = lifecycle,
        key2 = detailViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            detailViewModel.detailModelState.collect { value = it }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopAppBar(title = "Medicamento", onBackClick = {
            navHostController.popBackStack()
        })
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
        .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = detailModelState.medicine.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Número de registro",
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = detailModelState.medicine.id,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { detailViewModel.openProspect() }) {
            Text(text = "Prospecto", fontSize = 18.sp)
        }
    }

}

@Composable
fun DetailTopAppBar(title: String, onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = title, textAlign = TextAlign.Center)
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    modifier = Modifier,
                    contentDescription = "Atrás")
            }
        }
    )
}