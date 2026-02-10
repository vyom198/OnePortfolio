package com.vs.oneportfolio.core.database.realestate.history

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "soldestate")
data class SoldEstateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val propertyName: String,           // e.g., "Oak Street Apartment"
    val address : String ,       // e.g., "Residential", "Commercial", "Land"
    val properImg : String? = null ,
    val currentMarketValue: Double = 0.0,
    val purchasePrice: Double = 0.0,
)
