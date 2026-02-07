package com.vs.oneportfolio.main.mapper

import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity
import com.vs.oneportfolio.core.database.fixedincome.history.MaturedFEntity
import com.vs.oneportfolio.core.database.stocks.StocksEntity
import com.vs.oneportfolio.core.gemini.StockTransaction
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.model.getPayOutFrequency
import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI
import java.text.NumberFormat
import java.util.Locale

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