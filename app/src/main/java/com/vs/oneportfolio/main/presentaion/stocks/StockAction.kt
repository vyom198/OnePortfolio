package com.vs.oneportfolio.main.presentaion.stocks

import com.vs.oneportfolio.main.presentaion.home.HomeAction

sealed interface StockAction {
    data class onTextChange (val text : String): StockAction
    data object onButtonClick  : StockAction
    data object onAddIconclick : StockAction
    data object onDismiss : StockAction
}