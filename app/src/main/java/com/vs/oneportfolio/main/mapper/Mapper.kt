package com.vs.oneportfolio.main.mapper

import com.vs.oneportfolio.core.database.stocks.StocksEntity
import com.vs.oneportfolio.core.gemini.StockTransaction

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