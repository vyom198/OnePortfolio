package com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos

import kotlinx.serialization.Serializable

@Serializable
data class CoinMetadata(
    val symbol: String,
    val slug: String,
    val logo: String
)