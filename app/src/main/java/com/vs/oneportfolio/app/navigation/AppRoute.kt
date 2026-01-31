package com.vs.oneportfolio.app.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {

    @Serializable
    object  Home : AppRoute

}