package com.vs.oneportfolio.main.presentaion.home

import com.vs.oneportfolio.core.database.stocks.StocksEntity

data class HomeState(
  val totalPortfolioValue : Double = 0.00,
  val totalGain : Double = 0.00,
  val totalLossOrGain : String = "",
  val totalInvestment : Double = 20000.00
)