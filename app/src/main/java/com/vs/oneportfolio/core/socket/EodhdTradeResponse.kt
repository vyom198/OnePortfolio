package com.vs.oneportfolio.core.socket

import kotlinx.serialization.Serializable



@Serializable
data class TradeUpdate(
    val s: String,                // Symbol/Ticker (e.g., AAPL)
    val p: Double,                // Last trade price (one share)
    val v: Double,                // Trade size (volume)
    val c: List<Int>? = null,     // Trade condition codes
    val dp: Boolean? = false,     // Dark pool indicator
    val ms: String? = null,       // Market status (open/closed)
    val t: Long                   // Timestamp in Epoch ms
)