package com.vs.oneportfolio.main.presentaion.model



data class StockUI(
 val id: Long ,
 val ticker: String,
 val name: String,
 val quantity: Double,
 val averagePrice: Double,
 val currentPrice: Double,
 val lastUpdated: Long = System.currentTimeMillis(),
){
    val currentValue: Double
        get() = currentPrice * quantity

//    val totalInvestment: Double
//        get() = averagePrice * quantity

    // The raw math (needed for color logic)
    val rawGainOrLoss: Double
        get() = currentValue - averagePrice

    // 1. ABSOLUTE VALUE for the UI Text
    val absGainOrLoss: Double
        get() = kotlin.math.abs(rawGainOrLoss)

    // 2. ABSOLUTE PERCENTAGE for the UI Text
    val absPercentage: Double
        get() = if (averagePrice > 0) {
            kotlin.math.abs((rawGainOrLoss / averagePrice) * 100)
        } else {
            0.0
        }

    // 3. COLOR LOGIC (Still based on the raw value)
    val isPositive: Boolean
        get() = rawGainOrLoss >= 0


}