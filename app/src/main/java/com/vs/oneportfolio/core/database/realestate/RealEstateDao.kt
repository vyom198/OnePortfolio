package com.vs.oneportfolio.core.database.realestate

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RealEstateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRealEstate(realEstate: RealEstateEntity)

    @Query("SELECT * FROM realestate WHERE id = :id")
    suspend fun getRealEstateById(id: Int): RealEstateEntity?

    @Query("SELECT * FROM realestate")
    suspend fun getAllRealEstates(): List<RealEstateEntity>

    @Query("DELETE FROM realestate WHERE id = :id")
    suspend fun deleteRealEstateById(id: Int)

    @Query("UPDATE realestate SET mortgageBalance = :mortgageBalance WHERE id = :id")
    suspend fun updateMortgageBalance(id: Int, mortgageBalance: Double)

    @Update
    suspend fun updateRealEstate(realEstate: RealEstateEntity)


    @Query("SELECT SUM(currentMarketValue) FROM realestate")
    fun getTotalCurrentValue(): Flow<Double>

    @Query("SELECT SUM(purchasePrice) FROM realestate")
    fun getTotalInvested(): Flow<Double>

    @Query("SELECT COUNT(*) FROM realestate ")
    fun getCount(): Flow<Int>








}