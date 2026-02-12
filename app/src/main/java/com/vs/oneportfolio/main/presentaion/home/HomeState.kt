package com.vs.oneportfolio.main.presentaion.home
import kotlin.math.abs

data class HomeState(
  val totalCurrentValue: Double = 0.0,
  val totalInvested: Double = 0.0,
  val totalItemsInTrade : Int = 0 ,
  val totalItemsInCrypto : Int = 0,
  val totalCurrentValueOfCrypto : Double = 0.0,
  val totalInvestedInCrypto : Double = 0.0,
  val totalInvestedInFA : Double = 0.0,
  val totalCurrentValueInFA : Double = 0.0,
  val totalItemsinFA : Int = 0,
  val totalItemsInRealEstate : Int = 0,
  val totalCurrentValueInRealEstate : Double = 0.0,
  val totalInvestedInRealEstate : Double = 0.0,
  val totalCurrentValueOfMetals : Double = 0.0,
  val totalInvestedInMetals : Double = 0.0,
  val totalItemsInMetals : Int = 0,
  val cardClick : Boolean = false
) {
  val totalassets = totalItemsInMetals + totalItemsinFA + totalItemsInRealEstate + totalItemsInTrade + totalItemsInCrypto
  val totalInvestedInAssets = totalInvested + totalInvestedInCrypto + totalInvestedInFA + totalInvestedInRealEstate + totalInvestedInMetals

  val totalCurrentValueInAssets = totalCurrentValue + totalCurrentValueOfCrypto + totalCurrentValueInFA + totalCurrentValueInRealEstate + totalCurrentValueOfMetals
  val totalPnL: Double
    get() = totalCurrentValueInAssets - totalInvestedInAssets

  // Absolute value for UI display (e.g., "50.00" instead of "-50.00")
  val absPnL: Double
    get() = abs(totalPnL)

  // Helper for UI colors/icons
  val isPositive: Boolean
    get() = totalPnL >= 0

  // Percentage calculation (optional but very useful for portfolios)
  val pnlPercentage: Double
    get() = if (totalInvestedInAssets != 0.0) (totalPnL / totalInvestedInAssets) * 100 else 0.0

  // Display helpers
  val totalPortfolioValue: Double
    get() = totalCurrentValueInAssets

  val totalGainOrLossInTrade
    get() = totalCurrentValue - totalInvested

  val isTradePositive: Boolean
    get() = totalGainOrLossInTrade >= 0
  val tradeabs
    get() = abs(totalGainOrLossInTrade)

  val totalGainOrLossInCrypto
    get() = totalCurrentValueOfCrypto - totalInvestedInCrypto

  val isCryptoPositive: Boolean
    get() = totalGainOrLossInCrypto >= 0
  val cryptoabs
    get() = abs(totalGainOrLossInCrypto)

  val totalGainOrLossInFA
    get() = totalCurrentValueInFA - totalInvestedInFA

  val isAssetsPositive : Boolean
    get() =  totalGainOrLossInFA >=0

  val assetsAbs
    get() = abs(totalGainOrLossInFA)

  val totalGainOrLossInRealEstate
    get() = totalCurrentValueInRealEstate - totalInvestedInRealEstate

  val isRealEstatePositive : Boolean
    get() = totalGainOrLossInRealEstate >=0

  val realEstateAbs
    get() = abs(totalGainOrLossInRealEstate)

  val totalGainOrLossInMetals
    get() = totalCurrentValueOfMetals - totalInvestedInMetals

  val isMetalPositive : Boolean
    get() = totalGainOrLossInMetals >=0

  val MetsAbs
    get() = abs(totalGainOrLossInMetals)


}