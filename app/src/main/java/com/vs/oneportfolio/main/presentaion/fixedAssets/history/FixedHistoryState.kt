package com.vs.oneportfolio.main.presentaion.fixedAssets.history

import com.vs.oneportfolio.core.database.fixedincome.history.MaturedFEntity

data class FixedHistoryState(

    val list: List<MaturedFEntity> = emptyList(),
)