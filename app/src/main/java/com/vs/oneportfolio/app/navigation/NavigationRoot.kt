package com.vs.oneportfolio.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vs.oneportfolio.main.presentaion.crypto.CryptoRoot
import com.vs.oneportfolio.main.presentaion.fixedAssets.FixedAssetsRoot
import com.vs.oneportfolio.main.presentaion.home.HomeRoot
import com.vs.oneportfolio.main.presentaion.stocks.StockRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = AppRoute.Home,

    ) {
        composable<AppRoute.Home> {
            HomeRoot(
                onNavigateToStock = {
                    navController.navigate(AppRoute.Stock) {
                        launchSingleTop = true
                    }
                },
                onNavigateToCrypto = {
                    navController.navigate(AppRoute.Crypto) {
                        launchSingleTop = true

                    }
                } ,
                onNavigateToFA = {
                      navController.navigate(AppRoute.FA) {
                        launchSingleTop = true

                    }
                }

            )

        }
        composable<AppRoute.Stock> {
            StockRoot(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
          composable<AppRoute.Crypto> {
              CryptoRoot(
                  onBackClick = {
                      navController.popBackStack()
                  }
              )
          }


        composable<AppRoute.FA> {
            FixedAssetsRoot (
                onBackClick = {
                    navController.popBackStack()
                }
            )



        }

    }
}