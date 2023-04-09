package com.example.jetpackcomposemedicines

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackcomposemedicines.detailview.DetailUI
import com.example.jetpackcomposemedicines.detailview.DetailViewModel
import com.example.jetpackcomposemedicines.searchview.SearchUI
import com.example.jetpackcomposemedicines.searchview.SearchViewModel

@ExperimentalComposeUiApi
@Composable
fun AppNavHost(navHostController: NavHostController){

    NavHost(
        navController = navHostController,
        startDestination = NavRoutes.Search.route
    ){
        composable(NavRoutes.Search.route) {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchUI(
                navHostController = navHostController,
                searchViewModel = searchViewModel)

        }

        composable(
            "${NavRoutes.Detail.route}?id={id}", arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            val detailViewModel = hiltViewModel<DetailViewModel>()
            DetailUI(
                navHostController = navHostController,
                detailViewModel = detailViewModel)
        }
    }

}