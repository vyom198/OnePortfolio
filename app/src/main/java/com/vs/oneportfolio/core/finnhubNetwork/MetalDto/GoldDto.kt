package com.vs.oneportfolio.core.finnhubNetwork.MetalDto

import kotlinx.serialization.Serializable

@Serializable
data class GoldDto(
    val name: String,
    val price: Double,
    val symbol: String,
    val updatedAt: String,
    val updatedAtReadable: String
)