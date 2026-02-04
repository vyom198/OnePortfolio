package com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteData(
    val price: Double?,
    @SerialName("last_updated")
    val lastUpdated: String?
)

// USD quote wrapper
@Serializable
data class USDQuote(
    val USD: QuoteData?
)

// Coin data structure
@Serializable
data class CoinQuoteData(
    val quote: USDQuote?
)


// Status (optional if you want to check)
@Serializable
data class Status(
    val timestamp: String?,
    @SerialName("error_code")
    val errorCode: Int?
)

@Serializable
data class CmcQuoteResponse(
    val data: Map<String, CoinQuoteData>? = null,
    val status: Status?
)