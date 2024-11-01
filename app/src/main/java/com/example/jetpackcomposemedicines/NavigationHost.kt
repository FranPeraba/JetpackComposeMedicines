package com.example.jetpackcomposemedicines

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jetpackcomposemedicines.detailview.DetailUI
import com.example.jetpackcomposemedicines.detailview.DetailViewModel
import com.example.jetpackcomposemedicines.searchview.SearchUI
import com.example.jetpackcomposemedicines.searchview.SearchViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SearchView) {
        composable<SearchView> {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchUI(
                onMedicineClicked = { id -> navController.navigate(DetailView(id = id)) },
                searchViewModel = searchViewModel
            )
        }
        composable<DetailView> { backStackEntry ->
            val detailView: DetailView = backStackEntry.toRoute()
            val detailViewModel = hiltViewModel<DetailViewModel, DetailViewModel.DetailViewModelFactory> { factory ->
                factory.create(detailView.id)
            }
            DetailUI(
                onBackClicked = { navController.popBackStack() },
                detailViewModel = detailViewModel
            )
        }
    }
}