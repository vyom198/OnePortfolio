package com.vs.oneportfolio.main.presentaion.model

import com.vs.oneportfolio.main.presentaion.fixedAssets.components.model.PayOutType

data class FixedAssetUI(
    val id: Int = 0,
    val depositName: String,
    val InstitutionName: String ? = null,
    val amtPrincipal: Double,
    val interestRatePercent: Double,
    val currentValue : Double = 0.0,
    val payoutFrequencyMonths: PayOutType,
    val isCumulative: Boolean = true,
    val dateOpened: Long,
    val dateMaturity: Long,
    val notifyOnMaturity: Boolean = true,
    val notifyOnInterestCredit: Boolean = false,
    val leadTimeDays: Int = 7
)
