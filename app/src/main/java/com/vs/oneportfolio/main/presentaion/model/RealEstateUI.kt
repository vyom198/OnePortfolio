package com.vs.oneportfolio.main.presentaion.model

data class RealEstateUI(
    val id: Int = 0,
    val propertyName: String = "",
    val address: String = "",
    val propertyType: String = "",
    val properImg : String? = null ,
    // Financials
    val purchasePrice: Double = 0.0,
    val yieldRate : Double ,
    val currentMarketValue: Double = purchasePrice,
    val purchaseDate: Long,

    // Revenue & Expenses
    val monthlyRent: Double? = null,
    val isRented: Boolean = false,

    val mortgageReminder : Boolean = false ,
    val taxReminder : Boolean = false ,
    val rentReminder : Boolean = false ,

    val hasMortgage: Boolean = false,
    val mortgageBalance: Double? = 0.0,
    val mortgagePayment : Double? = 0.0 ,

    val taxDueDate: Long = 0L
)
