package com.vs.oneportfolio.core.database.fixedincome

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fixed_deposits")
data class FixedIncomeEntity(
    @PrimaryKey(autoGenerate = true) 
    val id: Int = 0,

    // --- Identification ---
    val depositName: String,             // e.g., "HDFC Tax Saver FD"
    val InstitutionName: String ? = null,                // e.g., "Barclays", "SBI"


    // --- Financials ---
    val amtPrincipal: Double,            // The original amount locked in
    val interestRatePercent: Double,     // The nominal rate (e.g., 7.0)
    val currentValue : Double = 0.0,
    // --- The "FD Logic" Fields ---
    /** * Payout Frequency in months. 
     * 1 = Monthly, 3 = Quarterly, 12 = Annual, 0 = At Maturity 
     */
     val payoutFrequencyMonths: Int = 0,
    
    /** * If true, interest is added back to principal (Compounding).
     * If false, interest is paid out to a bank account (Income).
     */
    val isCumulative: Boolean = true,

    // --- Timing ---
    val dateOpened: Long,                // Timestamp
    val dateMaturity: Long,              // Timestamp
    
    // --- Josh's Alert System ---
    val notifyOnMaturity: Boolean = true,
    val notifyOnInterestCredit: Boolean = false, // Alert when a monthly payout hits
    val leadTimeDays: Int = 7
)