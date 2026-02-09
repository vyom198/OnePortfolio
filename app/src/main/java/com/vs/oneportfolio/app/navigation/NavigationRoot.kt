package com.vs.oneportfolio.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vs.oneportfolio.main.presentaion.crypto.CryptoRoot
import com.vs.oneportfolio.main.presentaion.fixedAssets.FixedAssetsRoot
import com.vs.oneportfolio.main.presentaion.home.HomeRoot
import com.vs.oneportfolio.main.presentaion.metals.MetalRoot
import com.vs.oneportfolio.main.presentaion.realestate.RealRoot
import com.vs.oneportfolio.main.presentaion.realestate.addrealEstate.AddEstateRoot
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
                },
                onNavigateToRealEstate = {
                    navController.navigate(AppRoute.RealEstate) {
                        launchSingleTop = true

                    }
                },
                onNavigateToMetal = {
                    navController.navigate(AppRoute.Metals){
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

        composable<AppRoute.RealEstate> {
            RealRoot (
                 onBack = {
                     navController.popBackStack()
                 },
            onAddClick = {
                navController.navigate(AppRoute.AddEstate(it)) {
                    launchSingleTop = true
                }
            },
            onEditClick = { screen, id ->
                navController.navigate(AppRoute.AddEstate(screen, id)) {
                    launchSingleTop = true
                }
             }

            )

        }

        composable<AppRoute.AddEstate>{
            AddEstateRoot(
                onBack = {
                    navController.popBackStack()
                }
            )

        }

        composable<AppRoute.Metals> {
            MetalRoot (
                onBackClick = {
                    navController.popBackStack()
                }
            )


        }

    }
}