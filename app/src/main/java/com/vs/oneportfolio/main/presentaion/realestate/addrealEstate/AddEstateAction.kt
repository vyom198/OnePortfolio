package com.vs.oneportfolio.main.presentaion.realestate.addrealEstate

import android.net.Uri

sealed interface AddEstateAction {
    data class OnFieldUpdate(val field: EstateField, val value: String) : AddEstateAction
    data class OnFieldCancel(val field: EstateField) : AddEstateAction
    data class  onRentedChange(val value : Boolean) : AddEstateAction
    data class  onMortgageChange(val value : Boolean) : AddEstateAction
    data class  onPurchaseDateChange ( val value : Long?) : AddEstateAction
    data class  onTaxDateChange ( val value : Long?) : AddEstateAction
    object OnSave : AddEstateAction

    data object onChangeAvtar :AddEstateAction

    data class  onUriGet(val uri : Uri) : AddEstateAction

}