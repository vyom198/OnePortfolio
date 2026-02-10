package com.vs.oneportfolio.core.database.metals

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MetalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetal(metal: MetalEntity)

    @Query("SELECT * FROM metals")
    fun getAllMetals(): Flow<List<MetalEntity>>

    @Query("SELECT * FROM metals WHERE id = :id")
    suspend fun getMetalById(id: Long): MetalEntity

    @Query("DELETE FROM metals WHERE id = :id")
    suspend fun deleteMetalById(id: Long)

    @Query("SELECT COUNT(*) FROM metals")
    fun getCount() : Flow<Int>

    @Query("SELECT SUM(currentPrice) FROM metals")
    fun gettotalCurrentValue():Flow<Double>

    @Query("SELECT SUM(purchasePrice) FROM metals")
    fun getTotalInvestedValue() : Flow<Double>

    @Query("UPDATE metals SET currentPrice = :currentPrice WHERE id = :id")
    suspend fun updateMetal(id: Long, currentPrice: Double)





}

fun getKaratFactor(karat: Int): Double {
    return when (karat) {
        24 -> 1.0      // 24K = 100% pure
        22 -> 0.9167   // 22K = 91.67% pure
        21 -> 0.875    // 21K = 87.5% pure
        20 -> 0.8333   // 20K = 83.33% pure
        18 -> 0.75     // 18K = 75% pure
        16 -> 0.6667   // 16K = 66.67% pure
        14 -> 0.5833   // 14K = 58.33% pure
        12 -> 0.5      // 12K = 50% pure
        10 -> 0.4167   // 10K = 41.67% pure
        9 -> 0.375     // 9K = 37.5% pure
        8 -> 0.3333    // 8K = 33.33% pure
        else -> karat / 24.0 // Generic calculation
    }
}
