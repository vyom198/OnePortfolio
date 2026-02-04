package com.vs.oneportfolio.main.presentaion.crypto

import com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos.CoinMetadata
import com.vs.oneportfolio.core.finnhubNetwork.stockDtos.StockTicker
import com.vs.oneportfolio.main.presentaion.model.CryptoUI

data class CryptoState(
    val text : String = "",
    val loading : Boolean = false,
    val cryptoList : List<CryptoUI> = emptyList(),
    val tickerList : List<CoinMetadata> = emptyList(),
    val isAdding : Boolean = false,
    val selectedTicker : CoinMetadata? = null,
    val addingShare : Boolean = false,
    val currentUpdatingCrypto : CryptoUI? = null
)