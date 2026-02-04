package com.vs.oneportfolio.main.presentaion.stocks

import com.vs.oneportfolio.core.finnhubNetwork.StockTicker
import com.vs.oneportfolio.main.presentaion.home.HomeAction

sealed interface StockAction {
    data class onTextChange (val text : String): StockAction
    data object onButtonClick  : StockAction
    data object onAddIconclick : StockAction
    data object onDismiss : StockAction

    data object Clear : StockAction

    data class  onSelect(val tickerItem : StockTicker) : StockAction
}