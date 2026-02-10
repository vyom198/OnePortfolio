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
    data class OnMenuClick(val name : StockUI ) : StockAction
    data class onUpdateClick(val quantity : Int , val amt : Double)  : StockAction
    data class  onSelect(val tickerItem : StockTicker) : StockAction



    data object  onEditShareClick : StockAction
    data object  onDelete : StockAction
    data object  onDeleteConfirm : StockAction
    data object onDeleteCancel : StockAction
    data object onSoldClick : StockAction

}