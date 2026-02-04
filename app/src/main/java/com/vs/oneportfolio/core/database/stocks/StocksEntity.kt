package com.vs.oneportfolio.core.database.stocks

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp


@Entity(tableName = "stocks")
data class StocksEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val ticker: String,
    val name: String,
    val quantity: Double = 0.0,
    val averagePrice: Double = 0.0,
    val currentPricePerShare: Double = 0.0,
    val totalCurrentValue: Double = quantity * currentPricePerShare,
    val lastUpdated: Long = 0,
    val currency: String = "USD",
    val exchange: String? = null
)