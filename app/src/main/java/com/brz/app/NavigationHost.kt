package com.brz.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brz.app.ui.screen.DetailScreen
import com.brz.app.ui.screen.MainScreen

@Composable
fun NavigationHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Route.MainScreen.route) {

        composable(Route.MainScreen.route) {
            MainScreen(navController)
        }

        composable(Route.DetailScreen.route) {
            DetailScreen(user = it.arguments?.getString("user") ?: "0")
        }
    }
}