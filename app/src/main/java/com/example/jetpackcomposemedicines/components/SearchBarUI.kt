package com.example.jetpackcomposemedicines.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.jetpackcomposemedicines.R

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun SearchBarUI(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    matchesFound: Boolean,
    minQueryLength: Int,
    results: @Composable () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                keyboardController?.hide()
                return Offset.Zero
            }
        }
    }

    var active by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .semantics { isTraversalGroup = true }
                .zIndex(1f)
                .fillMaxWidth()
        ) {
            SearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp),
                query = searchText,
                onQueryChange = onSearchTextChanged,
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = {
                    Text(
                        text = placeholderText,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search_medicines))
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = onClearClick) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = stringResource(
                                    R.string.icn_search_clear_content_description)
                            )
                        }
                    }
                }
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(nestedScrollConnection)
                        .verticalScroll(rememberScrollState())
                        .background(Color.White)
                ) {
                    if (matchesFound) {
                        results()
                    } else {
                        if (searchText.length >= minQueryLength) {
                            NoSearchResults()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun NoSearchResults() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_results),
            fontSize = 18.sp)
    }
}


@ExperimentalComposeUiApi
@Preview(showSystemUi = true)
@Composable
fun Preview() {
    SearchBarUI(
        searchText = "",
        stringResource(R.string.place_holder_search_bar),
        matchesFound = true,
        minQueryLength = 4)

}