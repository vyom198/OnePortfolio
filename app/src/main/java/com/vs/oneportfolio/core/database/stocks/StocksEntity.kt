package com.vs.oneportfolio.core.database.stocks

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stocks")
data class StocksEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val ticker: String,
    val name: String,
    val quantity: Double,
    val averagePrice: Double,
    val currentPricePerShare: Double,
    val totalCurrentValue: Double = quantity * currentPricePerShare,
    val lastUpdated: Long = System.currentTimeMillis(),
    val currency: String = "USD",
    val exchange: String? = null
)