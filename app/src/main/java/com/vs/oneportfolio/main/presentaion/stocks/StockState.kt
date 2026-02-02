package com.vs.oneportfolio.main.presentaion.stocks

import com.vs.oneportfolio.main.presentaion.model.StockUI

data class StockState(
    val text : String = "",
    val loading : Boolean = false,
    val stocksList : List<StockUI> = emptyList(),
    val isAdding : Boolean = false
)