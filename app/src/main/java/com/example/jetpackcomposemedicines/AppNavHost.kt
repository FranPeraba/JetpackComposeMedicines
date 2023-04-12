package com.example.jetpackcomposemedicines

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposemedicines.detailview.DetailUI
import com.example.jetpackcomposemedicines.searchview.SearchUI

@ExperimentalComposeUiApi
@Composable
fun AppNavHost(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Search.route
    ){
        composable(route = NavRoutes.Search.route) {
            SearchUI(
                onMedicineClicked = { navController.navigate(route = "${NavRoutes.Detail.route}?id=${it.id}") }
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
            DetailUI(
                onBackClicked = { navController.popBackStack() }
            )
        }
    }

}