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

    @Query("UPDATE metals SET currentPrice = :currentPrice*weight WHERE id = :id")
    suspend fun updateMetal(id: Long, currentPrice: Double)





}