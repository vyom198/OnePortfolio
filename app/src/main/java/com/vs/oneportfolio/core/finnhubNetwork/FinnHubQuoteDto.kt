package com.vs.oneportfolio.core.finnhubNetwork

import kotlinx.serialization.Serializable

@Serializable
data class FinnHubQuoteDto(
    val c: Double,
    val h: Double,
    val l: Double,
    val o: Double,
    val pc: Double,
    val t: Long
)