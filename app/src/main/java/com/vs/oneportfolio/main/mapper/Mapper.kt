package com.vs.oneportfolio.main.mapper

import com.vs.oneportfolio.core.database.stocks.StocksEntity
import com.vs.oneportfolio.core.gemini.StockTransaction

fun StockTransaction.toEntity () : StocksEntity{
    return StocksEntity(
        ticker = ticker,
        name = name,
        quantity = quantity,
        averagePrice = totalBoughtPrice,
        currentPrice = 0.0

    )
}