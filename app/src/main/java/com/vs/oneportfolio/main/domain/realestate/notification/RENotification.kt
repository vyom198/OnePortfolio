package com.vs.oneportfolio.main.domain.realestate.notification


interface RENotification {

    fun showOnYield(
        id : String,
        name : String ,
        oldMoney : String,
        newMoney : String
    )




    fun showRentalNotification(
        id : String ,
        name : String ,
        money : String
    )
    fun showOnMortgageConfirmation(
        id : Int ,
        name : String ,
        money : String
    )
    fun showOnTaxPayment(
        id : Int ,
        name : String,
        date : String
    )

    fun cancelNotification (
        id: Int
    )
}