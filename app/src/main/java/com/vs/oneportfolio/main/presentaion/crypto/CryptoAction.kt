package com.vs.oneportfolio.main.presentaion.crypto

import com.vs.oneportfolio.core.finnhubNetwork.StockTicker
import com.vs.oneportfolio.main.presentaion.model.CryptoUI

sealed interface CryptoAction {
    data class onTextChange (val text : String): CryptoAction
    data object onButtonClick  : CryptoAction
    data object onAddIconclick : CryptoAction
    data object onDismiss : CryptoAction
    data object onDismissUpdate :CryptoAction
    data object Clear : CryptoAction
    data class AddShare(val name : CryptoUI ) : CryptoAction
    data class onUpdateClick(val quantity : Double, val amt : Double)  : CryptoAction
    data class  onSelect(val tickerItem : StockTicker) : CryptoAction
}