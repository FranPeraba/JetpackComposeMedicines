package com.example.jetpackcomposemedicines

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposemedicines.detailview.DetailUI
import com.example.jetpackcomposemedicines.detailview.DetailViewModel
import com.example.jetpackcomposemedicines.searchview.SearchUI
import com.example.jetpackcomposemedicines.searchview.SearchViewModel

@ExperimentalComposeUiApi
@Composable
fun AppNavHost(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Search.route
    ){
        composable(route = NavRoutes.Search.route) {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchUI(
                onMedicineClicked = { navController.navigate(route = "${NavRoutes.Detail.route}?id=${it.id}") },
                searchViewModel = searchViewModel
            )
        }

        composable(
            route = "${NavRoutes.Detail.route}?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            val detailViewModel = hiltViewModel<DetailViewModel>()
            DetailUI(
                onBackClicked = { navController.popBackStack() },
                detailViewModel = detailViewModel
            )
        }
    }

}