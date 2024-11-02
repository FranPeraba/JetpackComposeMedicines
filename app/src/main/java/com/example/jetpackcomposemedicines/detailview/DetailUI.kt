package com.example.jetpackcomposemedicines.detailview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.jetpackcomposemedicines.R
import com.example.jetpackcomposemedicines.ui.theme.JetpackComposeMedicinesTheme
import com.example.jetpackcomposemedicines.ui.theme.Shapes
import java.util.Locale

@ExperimentalMaterial3Api
@Composable
fun DetailUI(
    onBackClicked: () -> Unit = {},
    detailViewModel: DetailViewModel = viewModel()) {

    val context = LocalContext.current
    val detailUiState by detailViewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopAppBar(
            title = stringResource(R.string.medicine),
            onBackClick = onBackClicked
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        if (detailUiState.showProgressBar)
            CircularProgressIndicator()
        else if (detailUiState.showError) {
            Image(
                painterResource(id = R.drawable.ic_connection_error),
                contentDescription = stringResource(R.string.error_connection))
        } else {
            Row(
                modifier = Modifier.wrapContentSize(Alignment.Center)
            ) {
                if (detailUiState.medicine.photos.size == 2) {
                    AsyncImage(
                        model = detailUiState.medicine.photos[0].url,
                        modifier = Modifier
                            .size(200.dp)
                            .weight(1f),
                        contentScale = ContentScale.Fit,
                        fallback = painterResource(R.drawable.no_disponible),
                        contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    AsyncImage(
                        model = detailUiState.medicine.photos[1].url,
                        modifier = Modifier
                            .size(200.dp)
                            .weight(1f),
                        contentScale = ContentScale.Fit,
                        fallback = painterResource(R.drawable.no_disponible),
                        contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = detailUiState.medicine.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.registry),
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = detailUiState.medicine.id,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(12.dp),
                shape = Shapes.medium,
                onClick = { openProspect(detailUiState, context) }) {
                Text(
                    text = stringResource(R.string.prospect).uppercase(Locale.ROOT),
                    fontSize = 18.sp,
                    color = Color.White)
            }
        }
    }
}

private fun openProspect(detailUiState: DetailUiState, context: Context) {
    if (detailUiState.medicine.docs.size == 2) {
        val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse(detailUiState.medicine.docs[1].urlHtml ?:
            detailUiState.medicine.docs[1].url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } else {
        Toast.makeText(context, context.resources.getString(R.string.no_prospect), Toast.LENGTH_SHORT).show()
    }
}

@ExperimentalMaterial3Api
@Composable
fun DetailTopAppBar(
    title: String,
    onBackClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back))
            }
        }
    )
}

@ExperimentalMaterial3Api
@Composable
@Preview()
fun DefaultPreview() {
    //DetailTopAppBar(title = "Medicamento")
    JetpackComposeMedicinesTheme {
        Button(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(12.dp),
            shape = Shapes.medium,
            onClick = {  }) {
            Text(
                text = stringResource(R.string.prospect).uppercase(Locale.ROOT),
                fontSize = 18.sp,
                color = Color.White)
        }
    }
}