package com.vs.oneportfolio.main.presentaion.home

sealed interface HomeAction {
    data object OnCardClick : HomeAction
}