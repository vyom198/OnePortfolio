package com.vs.oneportfolio.core.database.realestate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "realestate")
data class RealEstateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val propertyName: String,           // e.g., "Oak Street Apartment"
    val address: String?= null,
    val propertyType: String,           // e.g., "Residential", "Commercial", "Land"
    val properImg : String? = null ,
    // Financials
    val purchasePrice: Double,
    val yieldRate : Double = 0.0,
    val currentMarketValue: Double = 0.0,
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

    val taxDueDate: Long

)
