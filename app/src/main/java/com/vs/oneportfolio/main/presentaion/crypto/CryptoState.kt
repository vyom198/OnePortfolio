package com.vs.oneportfolio.main.presentaion.crypto

import com.vs.oneportfolio.core.finnhubNetwork.StockTicker
import com.vs.oneportfolio.main.presentaion.model.CryptoUI
import com.vs.oneportfolio.main.presentaion.model.StockUI

data class CryptoState(
    val text : String = "",
    val loading : Boolean = false,
    val cryptoList : List<CryptoUI> = emptyList(),
    val tickerList : List<StockTicker> = emptyList(),
    val isAdding : Boolean = false,
    val selectedTicker : StockTicker? = null,
    val addingShare : Boolean = false,
    val currentUpdatingCrypto : CryptoUI? = null
)