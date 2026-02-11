package com.vs.oneportfolio.core.database.crypto

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrypto(crypto: CryptoEntity)

    @Query("SELECT * FROM crypto")
    fun getAllCrypto(): Flow<List<CryptoEntity>>

    @Query("SELECT * FROM crypto")
    suspend fun getAllCryptoSnap(): List<CryptoEntity>

    @Query("DELETE FROM crypto WHERE id = :id")
    suspend fun deleteCrypto(id: Long)



    @Query("SELECT SUM(totalCurrentValue) FROM crypto")
    fun getTotalCurrentValue(): Flow<Double>
    @Query("SELECT SUM(totalCurrentValue) FROM crypto")
     suspend fun getTotalCurrentValueSnap(): Double
    @Query("SELECT SUM(averagePrice) FROM crypto")
    fun getTotalInvested(): Flow<Double>

    @Query("SELECT COUNT(*) FROM crypto")
    fun getCount(): Flow<Int>

    @Query("SELECT * FROM crypto WHERE id = :id")
    suspend fun getCryptoById(id: Long): CryptoEntity

    @Query("UPDATE crypto SET totalCurrentValue = :newPrice * quantity, lastUpdated = :newLastUpdated WHERE id = :id")
    suspend fun  updateStockbyId(id: Long, newPrice: Double, newLastUpdated: String)

}