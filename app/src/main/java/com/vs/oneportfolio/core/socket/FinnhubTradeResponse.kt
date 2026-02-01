package com.vs.oneportfolio.core.socket

import kotlinx.serialization.Serializable

@Serializable
data class FinnhubTradeResponse(
    val data: List<TradeData>? = null,
    val type: String
)

@Serializable
data class TradeData(
    val s: String, // Symbol (ticker)
    val p: Double, // Price
    val t: Long,   // Timestamp
    val v: Double  // Volume
)