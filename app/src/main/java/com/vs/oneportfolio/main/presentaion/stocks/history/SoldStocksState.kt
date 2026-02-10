package com.vs.oneportfolio.main.presentaion.stocks.history

import com.vs.oneportfolio.core.database.stocks.history.SoldStockEntity

data class SoldStocksState(

    val soldstocks: List<SoldStockEntity> = emptyList(),
)