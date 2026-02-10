package com.vs.oneportfolio.main.presentaion.metals.history

import com.vs.oneportfolio.core.database.metals.history.SoldMetalEntity

data class SoldMetalState(

    val list : List<SoldMetalEntity> = emptyList(),
)