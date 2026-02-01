package com.vs.oneportfolio.core.database.stocks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: StocksEntity)

    @Query("SELECT * FROM stocks")
    fun getAllStocks(): Flow<List<StocksEntity>>

    @Query("DELETE FROM stocks WHERE id = :id")
    suspend fun deleteStock(id: Long)


    @Query("UPDATE stocks SET currentPrice = :price WHERE ticker = :ticker")
    suspend fun updateStock(ticker: String , price: Double)
}



