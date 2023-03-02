package com.example.jetpackcomposemedicines

sealed class NavRoutes(val route: String) {
    object Search: NavRoutes("search")
    object Detail: NavRoutes("detail")
}
