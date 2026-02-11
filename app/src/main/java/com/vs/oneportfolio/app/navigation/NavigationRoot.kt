package com.vs.oneportfolio.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vs.oneportfolio.main.presentaion.crypto.CryptoRoot
import com.vs.oneportfolio.main.presentaion.crypto.history.SoldCryptoRoot
import com.vs.oneportfolio.main.presentaion.fixedAssets.FixedAssetsRoot
import com.vs.oneportfolio.main.presentaion.fixedAssets.history.FixedHistoryRoot
import com.vs.oneportfolio.main.presentaion.home.HomeRoot
import com.vs.oneportfolio.main.presentaion.metals.MetalRoot
import com.vs.oneportfolio.main.presentaion.metals.history.SoldMetalRoot
import com.vs.oneportfolio.main.presentaion.portfoliohealth.PortfolioAnalysisRoot
import com.vs.oneportfolio.main.presentaion.portfoliohealth.detail.PortfolioSavedRoot
import com.vs.oneportfolio.main.presentaion.portfoliohealth.history.PortfolioHealthHistoryRoot
import com.vs.oneportfolio.main.presentaion.realestate.RealRoot
import com.vs.oneportfolio.main.presentaion.realestate.addrealEstate.AddEstateRoot
import com.vs.oneportfolio.main.presentaion.realestate.history.SoldEstateRoot
import com.vs.oneportfolio.main.presentaion.stocks.StockRoot
import com.vs.oneportfolio.main.presentaion.stocks.history.SoldStocksRoot

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

                },
                onNavigateToPortfolioHealth = {
                    navController.navigate(AppRoute.PortfolioAnalysis){
                        launchSingleTop = true
                    }
                }



            )

        }
        composable<AppRoute.Stock> {
            StockRoot(
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigateToSoldStocks = {
                    navController.navigate(AppRoute.SoldStock){
                        launchSingleTop = true
                    }
                }
            )
        }
          composable<AppRoute.Crypto> {
              CryptoRoot(
                  onBackClick = {
                      navController.popBackStack()
                  },
                  onNavigateToHistory = {
                      navController.navigate(AppRoute.SoldCrypto){
                          launchSingleTop = true
                      }
                  }
              )
          }


        composable<AppRoute.FA>{
            FixedAssetsRoot (
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigateToHistory = {
                    navController.navigate(AppRoute.SoldFixedAsset){
                        launchSingleTop = true
                    }

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
             },
                onNavigateToSoldEstate = {
                    navController.navigate(AppRoute.SoldRealEstate){
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
                },
                onNavigateToMetalHistory = {
                    navController.navigate(AppRoute.SoldMetal){
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<AppRoute.SoldCrypto> {
            SoldCryptoRoot (
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable<AppRoute.SoldFixedAsset> {
            FixedHistoryRoot {
                navController.popBackStack()
            }
        }

        composable<AppRoute.SoldMetal> {
            SoldMetalRoot {
                navController.popBackStack()
            }
        }
        composable<AppRoute.SoldRealEstate> {
            SoldEstateRoot {
                navController.popBackStack()
            }
        }
        composable<AppRoute.SoldStock> {
            SoldStocksRoot {
                navController.popBackStack()
            }
        }
        composable<AppRoute.PortfolioAnalysis> {
            PortfolioAnalysisRoot(
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigateToHistory = {
                     navController.navigate(AppRoute.PortfolioHistory){
                         launchSingleTop = true
                     }
                }

            )

        }
        composable<AppRoute.PortfolioHistory> {
            PortfolioHealthHistoryRoot(
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigateToDetail = {
                    navController.navigate(AppRoute.PortfolioSaved(it)){
                        launchSingleTop = true

                    }
                }

            )
        }

        composable<AppRoute.PortfolioSaved> {
            PortfolioSavedRoot {
                navController.popBackStack()
            }
        }
    }
}