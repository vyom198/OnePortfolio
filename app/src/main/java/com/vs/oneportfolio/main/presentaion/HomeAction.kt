package com.vs.oneportfolio.main.presentaion 
sealed interface HomeAction {
    data class onTextChange (val text : String): HomeAction
    data object onButtonClick  : HomeAction
}