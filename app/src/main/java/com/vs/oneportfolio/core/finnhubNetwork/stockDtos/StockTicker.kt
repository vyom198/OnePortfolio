package com.vs.oneportfolio.core.finnhubNetwork.stockDtos

import kotlinx.serialization.Serializable

@Serializable
data class StockTicker(
val description: String,     // e.g., "APPLE INC"
val displaySymbol: String,  // e.g., "AAPL"
val symbol: String,         // e.g., "AAPL"
val type: String            // e.g., "Common Stock"
)
@Serializable
data class FinnHubSearchResponse(
    val count: Int,
    val result: List<StockTicker> // This uses the StockTicker class we made before
)