package com.vs.oneportfolio.main.presentaion.home
import kotlin.math.abs

data class HomeState(
  val totalCurrentValue: Double = 0.0,
  val totalInvested: Double = 0.0,
  val totalItemsInTrade : Int = 0
) {

  val totalPnL: Double
    get() = totalCurrentValue - totalInvested

  // Absolute value for UI display (e.g., "50.00" instead of "-50.00")
  val absPnL: Double
    get() = abs(totalPnL)

  // Helper for UI colors/icons
  val isPositive: Boolean
    get() = totalPnL >= 0

  // Percentage calculation (optional but very useful for portfolios)
  val pnlPercentage: Double
    get() = if (totalInvested != 0.0) (totalPnL / totalInvested) * 100 else 0.0

  // Display helpers
  val totalPortfolioValue: Double
    get() = totalCurrentValue

  val totalGainOrLossInTrade
    get() = totalCurrentValue - totalInvested

  val isTradePositive: Boolean
    get() = totalGainOrLossInTrade >= 0
  val tradeabs
    get() = abs(totalGainOrLossInTrade)

}