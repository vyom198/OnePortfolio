package com.vs.oneportfolio.core.database.fixedincome

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FixedIcomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFixedIncome(fixedIncome: FixedIncomeEntity)

    @Query("SELECT * FROM fixed_deposits WHERE id = :id")
    suspend fun getFixedIncomeById(id: Int): FixedIncomeEntity?

    @Query("DELETE FROM fixed_deposits WHERE id = :id")
    suspend fun deleteFixedIncomeById(id: Int)

    @Query("SELECT * FROM fixed_deposits")
    fun getAllFixedIncome(): Flow<List<FixedIncomeEntity>>

    @Query("SELECT * FROM fixed_deposits")
    fun getAllFixedIncomeSnap(): List<FixedIncomeEntity>
    @Query("SELECT SUM(amtPrincipal) FROM fixed_deposits")
    fun getTotalInvested(): Flow<Double>

    @Query("SELECT COUNT(*) FROM fixed_deposits")
    fun getCount(): Flow<Int>

    @Query("SELECT SUM(currentValue) FROM fixed_deposits")
    fun getCurrentValue(): Flow<Double>
    @Query("SELECT SUM(currentValue) FROM fixed_deposits")
    suspend fun getCurrentValueSnap(): Double
    @Query("UPDATE fixed_deposits SET notifyOnInterestCredit = :enabled WHERE id = :id")
    suspend fun updatePaymentNotify(id: Int, enabled: Boolean)

    @Query("UPDATE fixed_deposits SET notifyOnMaturity = :enabled WHERE id = :id")
    suspend fun updateMaturityNotify(id: Int, enabled: Boolean)
}