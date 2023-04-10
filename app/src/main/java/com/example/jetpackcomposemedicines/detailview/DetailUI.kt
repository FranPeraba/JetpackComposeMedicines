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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.example.jetpackcomposemedicines.R
import java.util.*

@Composable
fun DetailUI(navHostController: NavHostController, detailViewModel: DetailViewModel) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current

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
        DetailTopAppBar(title = stringResource(R.string.medicine), onBackClick = {
            navHostController.popBackStack()
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        if (detailModelState.showProgressBar)
            CircularProgressIndicator()
        else if (detailModelState.showError) {
            Image(
                painterResource(id = R.drawable.ic_connection_error),
                contentDescription = stringResource(R.string.error_connection))
        } else {
            Text(
                text = detailModelState.medicine.name,
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
                text = detailModelState.medicine.id,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { openProspect(detailModelState, context) }) {
                Text(
                    text = stringResource(R.string.prospect).uppercase(Locale.ROOT),
                    fontSize = 18.sp)
            }
        }
    }

}

fun openProspect(detailModelState: DetailModelState, context: Context) {
    if (detailModelState.medicine.docs.size >= 2) {
        val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse(detailModelState.medicine.docs[1].urlHtml
                ?: detailModelState.medicine.docs[1].url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } else {
        Toast.makeText(context, context.resources.getString(R.string.no_prospect),
            Toast.LENGTH_SHORT).show()
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
                    contentDescription = stringResource(R.string.back))
            }
        }
    )
}