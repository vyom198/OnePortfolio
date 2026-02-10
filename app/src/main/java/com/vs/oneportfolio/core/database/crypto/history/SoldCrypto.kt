package com.vs.oneportfolio.core.database.crypto.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "soldCrypto")
data class SoldCrypto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val quantity: Double = 0.0,
    val totalCurrentValue: Double,

)
