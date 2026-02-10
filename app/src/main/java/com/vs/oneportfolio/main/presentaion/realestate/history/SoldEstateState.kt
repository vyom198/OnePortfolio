package com.vs.oneportfolio.main.presentaion.realestate.history

import com.vs.oneportfolio.core.database.realestate.history.SoldEstateEntity

data class SoldEstateState(
    val list: List<SoldEstateEntity> = emptyList(),
)