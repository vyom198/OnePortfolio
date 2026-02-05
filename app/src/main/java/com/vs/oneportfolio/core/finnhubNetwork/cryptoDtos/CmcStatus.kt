package com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CmcStatus(
    @SerialName("error_code") val errorCode: Int,
    @SerialName("error_message") val errorMessage: String?
)