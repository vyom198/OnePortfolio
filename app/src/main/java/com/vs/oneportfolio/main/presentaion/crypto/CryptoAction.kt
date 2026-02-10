package com.vs.oneportfolio.main.presentaion.crypto

import com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos.CoinMetadata
import com.vs.oneportfolio.core.finnhubNetwork.stockDtos.StockTicker
import com.vs.oneportfolio.main.presentaion.model.CryptoUI
import com.vs.oneportfolio.main.presentaion.stocks.StockAction

sealed interface CryptoAction {
    data class onTextChange (val text : String): CryptoAction
    data object onButtonClick  : CryptoAction
    data object onAddIconclick : CryptoAction
    data object onDismiss : CryptoAction
    data object onDismissUpdate :CryptoAction
    data object Clear : CryptoAction

    data class onUpdateClick(val quantity : Double, val amt : Double)  : CryptoAction
    data class  onSelect(val cryptoItem : CoinMetadata) : CryptoAction

    data class OnMenuClick(val name : CryptoUI ) : CryptoAction
    data object  onEditShareClick : CryptoAction
    data object  onDeleting : CryptoAction
    data object  onDeleteConfirm : CryptoAction
    data object onDeleteCancel : CryptoAction
    data object onSoldClick : CryptoAction
}