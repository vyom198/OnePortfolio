package com.vs.oneportfolio.core.database.fixedincome

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fixed_deposits")
data class FixedIncomeEntity(
    @PrimaryKey(autoGenerate = true) 
    val id: Int = 0,

    val depositName: String,
    val InstitutionName: String ? = null,

    val amtPrincipal: Double,
    val interestRatePercent: Double,
    val currentValue : Double = amtPrincipal,
    val payoutFrequencyMonths: Int = 0,
    val isCumulative: Boolean = true,
    val dateOpened: Long,
    val dateMaturity: Long,

    val notifyOnMaturity: Boolean = false,
    val notifyOnInterestCredit: Boolean = false,
    val leadTimeDays: Int = 7
)