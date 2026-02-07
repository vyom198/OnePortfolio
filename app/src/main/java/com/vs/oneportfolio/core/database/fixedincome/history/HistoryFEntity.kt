package com.vs.oneportfolio.core.database.fixedincome.history

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "matured_fixed_deposit")
data class MaturedFEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val depositName: String,
    val InstitutionName: String ? = null,
    val amtPrincipal: Double,
    val interestRatePercent: Double,
    val currentValue : Double = amtPrincipal,
    val isCumulative: Boolean = true,
    val dateOpened: Long,
    val dateMaturity: Long,
)
