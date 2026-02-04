package com.vs.oneportfolio.core.database.crypto

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "crypto")
data class CryptoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val ticker: String,
    val name: String,
    val quantity: Double = 0.0,
    val averagePrice: Double = 0.0,
    val currentPricePerCoin: Double = 0.0,
    val totalCurrentValue: Double = quantity * currentPricePerCoin,
    val lastUpdated: Long = 0,
    val currency: String = "USD",
    val exchange: String? = null
)
