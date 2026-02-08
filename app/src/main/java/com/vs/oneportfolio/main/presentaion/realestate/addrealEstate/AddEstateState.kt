package com.vs.oneportfolio.main.presentaion.realestate.addrealEstate


data class AddEstateState(
   val screenTitle :String = "",
   val propertyName: String = "",
   val purchaseDate : Long? = null,
   val address: String = "",
   val propertyType: String = "",
   val properImg : String? = null ,
   val purchasePrice: String = "",
   val yieldRate : String = "" ,
   val monthlyRent: String = "",
   val isRented: Boolean = false,
   val hasMortgage: Boolean = false,
   val mortgageBalance: String = "" ,
   val mortgagePayment :String = "" ,
   val taxDueDate: Long? = null
   )

enum class EstateField {
    NAME, ADDRESS, TYPE, PRICE, YIELD, RENT,
    MORTGAGE_BAL, MORTGAGE_PAY,
}