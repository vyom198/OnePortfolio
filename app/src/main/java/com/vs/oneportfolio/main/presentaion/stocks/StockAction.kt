package com.vs.oneportfolio.main.presentaion.stocks

import com.vs.oneportfolio.core.finnhubNetwork.stockDtos.StockTicker
import com.vs.oneportfolio.main.presentaion.model.StockUI

sealed interface StockAction {
    data class onTextChange (val text : String): StockAction
    data object onButtonClick  : StockAction
    data object onAddIconclick : StockAction
    data object onDismiss : StockAction
    data object onDismissUpdate : StockAction
    data object Clear : StockAction
    data class AddShare(val name : StockUI ) : StockAction
    data class onUpdateClick(val quantity : Int , val amt : Double)  : StockAction
    data class  onSelect(val tickerItem : StockTicker) : StockAction
}