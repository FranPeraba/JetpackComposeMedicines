package com.example.jetpackcomposemedicines.detailview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposemedicines.R
import java.util.*

@Composable
fun DetailUI(
    onBackClicked: () -> Unit = {},
    detailViewModel: DetailViewModel = viewModel()) {

    val context = LocalContext.current

    val detailUiState by detailViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopAppBar(
            title = stringResource(R.string.medicine),
            onBackClick = { onBackClicked() }
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
                onClick = { openProspect(detailUiState, context) }) {
                Text(
                    text = stringResource(R.string.prospect).uppercase(Locale.ROOT),
                    fontSize = 18.sp)
            }
        }
    }

}

private fun openProspect(detailUiState: DetailUiState, context: Context) {
    if (detailUiState.medicine.docs.size >= 2) {
        val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse(detailUiState.medicine.docs[1].urlHtml
                ?: detailUiState.medicine.docs[1].url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } else {
        Toast.makeText(context, context.resources.getString(R.string.no_prospect),
            Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun DetailTopAppBar(
    title: String,
    onBackClick: () -> Unit = {}
) {
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
                    contentDescription = stringResource(R.string.back))
            }
        }
    )
}