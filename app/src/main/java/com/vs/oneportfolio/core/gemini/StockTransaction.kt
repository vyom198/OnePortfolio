package com.vs.oneportfolio.core.gemini

import kotlinx.serialization.Serializable

@Serializable
data class StockTransaction(
    val ticker: String,
    val name: String,
    val quantity: Double ,
    val totalBoughtPrice: Double
)