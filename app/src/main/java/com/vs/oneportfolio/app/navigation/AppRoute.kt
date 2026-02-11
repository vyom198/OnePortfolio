package com.vs.oneportfolio.app.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {

    @Serializable
    object  Home : AppRoute

    @Serializable
    object Stock : AppRoute

    @Serializable
    object Crypto : AppRoute

    @Serializable
    object FA : AppRoute

    @Serializable
    object RealEstate : AppRoute

    @Serializable
    data class AddEstate(val title : String, val id : Int? = null) : AppRoute

    @Serializable
    object Metals : AppRoute

    @Serializable
    object SoldCrypto : AppRoute

    @Serializable
    object SoldRealEstate : AppRoute

    @Serializable
    object SoldMetal : AppRoute

    @Serializable
    object SoldStock : AppRoute

    @Serializable
    object SoldFixedAsset : AppRoute

    @Serializable
    object PortfolioAnalysis : AppRoute





}



