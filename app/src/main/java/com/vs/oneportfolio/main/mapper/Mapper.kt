package com.vs.oneportfolio.main.mapper

import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity
import com.vs.oneportfolio.core.database.fixedincome.history.MaturedFEntity
import com.vs.oneportfolio.core.database.portfoliohealth.PortfolioHealthEntity
import com.vs.oneportfolio.core.gemini_firebase.ComprehensivePortfolioAnalysis
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.model.getPayOutFrequency
import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

//fun StockTransaction.toEntity () : StocksEntity{
//    return StocksEntity(
//        ticker = ticker,
//        name = name,
//        quantity = quantity,
//        averagePrice = totalBoughtPrice,
//        totalCurrentValue = totalBoughtPrice,
//        currentPricePerShare = 0.0
//    )
//}
fun Double.formats() : String {
    return "%.2f".format(this
    )
}
fun Double.formatsO(round: Boolean = true) : String {
    return if (round) {
        "%.0f".format(this)
    } else {
        this.toInt().toString()
    }
}
fun Double.toCommaString(
    minDecimals: Int = 0,
    maxDecimals: Int = 2,
    locale: Locale = Locale.US
): String {
    val formatter = NumberFormat.getInstance(locale).apply {
        minimumFractionDigits = minDecimals
        maximumFractionDigits = maxDecimals
    }
    return formatter.format(this)
}
fun formatLongToDate(timestamp: Long): String {
    // 1. Define the pattern (e.g., 15 Feb)
    val pattern = "dd MMM"

    // 2. Initialize SimpleDateFormat with a fixed Locale to avoid translation issues
    val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)

    // 3. (Optional) Set to system default or UTC depending on your needs
    sdf.timeZone = TimeZone.getDefault()

    // 4. Convert Long to Date and format
    return sdf.format(Date(timestamp)).lowercase()
}
fun FixedAssetUI.toEntity (): FixedIncomeEntity{
    return FixedIncomeEntity(
        id = id,
        depositName = depositName,
        InstitutionName = InstitutionName,
        amtPrincipal = amtPrincipal,
        interestRatePercent = interestRatePercent,
        currentValue = currentValue,
        payoutFrequencyMonths = payoutFrequencyMonths.getPayOutFrequency(),
        dateOpened = dateOpened,
        dateMaturity = dateMaturity
    )
}

fun FixedIncomeEntity.toMatured () : MaturedFEntity{
    return MaturedFEntity(
        id = id.toLong(),
        depositName = depositName,
        InstitutionName = InstitutionName,
        amtPrincipal = amtPrincipal,
        interestRatePercent = interestRatePercent,
        currentValue = currentValue,
        dateOpened = dateOpened,
        dateMaturity = dateMaturity
    )
}


fun PortfolioHealthEntity.toUI () : ComprehensivePortfolioAnalysis{
    return ComprehensivePortfolioAnalysis(
        healthScore = healthScore,
        overallRating = overallRating,
        riskLevel = riskLevel,
        diversificationAnalysis = diversificationAnalysis,
        riskAnalysis = riskAnalysis,
        recommendations = recommendations,
        performanceInsights = performanceInsights,
        executiveSummary = executiveSummary

    )
}