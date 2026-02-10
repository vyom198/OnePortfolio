package com.vs.oneportfolio.core.database.stocks.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SoldStockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSoldStock(soldStock: SoldStockEntity)

    @Query("SELECT * FROM soldstock")
    fun getAllSoldStocks(): Flow<List<SoldStockEntity>>

}