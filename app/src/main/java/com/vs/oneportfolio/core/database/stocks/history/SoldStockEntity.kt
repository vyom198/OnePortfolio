package com.vs.oneportfolio.core.database.stocks.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "soldstock")
data class SoldStockEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val quantity: Double = 0.0,
    val totalCurrentValue: Double = 0.0,
)
