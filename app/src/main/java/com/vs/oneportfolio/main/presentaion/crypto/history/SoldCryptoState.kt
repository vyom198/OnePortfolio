package com.vs.oneportfolio.main.presentaion.crypto.history

import com.vs.oneportfolio.core.database.crypto.history.SoldCrypto

data class SoldCryptoState(
    val soldCrypto: List<SoldCrypto> = emptyList(),
)