package com.vs.oneportfolio.main.presentaion.model

import com.vs.oneportfolio.main.mapper.formats
import com.vs.oneportfolio.main.mapper.toCommaString


data class StockUI(
 val id: Long ,
 val ticker: String,
 val name: String,
 val quantity:Int,
 val averagePrice: Double,
 val currentPrice : Double,
 val lastUpdated: Long = System.currentTimeMillis(),
){


    val rawGainOrLoss: Double
        get() = currentPrice - averagePrice

    // 1. ABSOLUTE VALUE for the UI Text
    val absGainOrLoss: String
        get() = kotlin.math.abs(rawGainOrLoss).toCommaString()

    // 2. ABSOLUTE PERCENTAGE for the UI Text
    val absPercentage: String
        get() = if (averagePrice > 0) {
            kotlin.math.abs((rawGainOrLoss / averagePrice) * 100)
        } else {
            0.0
        }.formats()

    // 3. COLOR LOGIC (Still based on the raw value)
    val isPositive: Boolean
        get() = rawGainOrLoss >= 0


}

