package com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos

import com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos.CmcStatus
import com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos.CoinMetadata
import kotlinx.serialization.Serializable

@Serializable
data class CmcMetadataResponse(
    val data: Map<String, CoinMetadata>, // Handles the dynamic "1", "9023" keys
    val status: CmcStatus
)