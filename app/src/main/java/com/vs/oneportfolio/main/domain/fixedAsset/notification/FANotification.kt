package com.vs.oneportfolio.main.domain.fixedAsset.notification

interface FANotification {
    fun showMaturedNotification(
        id : String ,
        name : String ,
        money : String
    )
    fun showOnPaymentConfirmation(
        id : Int ,
        name : String ,
        interest : Double ,
        compounding : Boolean
    )

    fun cancelNotification (
        id: Int
    )
}