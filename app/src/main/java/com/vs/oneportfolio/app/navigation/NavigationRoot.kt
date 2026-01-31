package com.vs.oneportfolio.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vs.oneportfolio.main.presentaion.HomeRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = AppRoute.Home,

    ) {
        composable<AppRoute.Home> {
            HomeRoot()

        }


    }
}