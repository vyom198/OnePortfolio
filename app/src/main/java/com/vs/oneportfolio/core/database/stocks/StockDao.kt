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


    @Query("UPDATE stocks SET totalCurrentValue = :price * quantity  WHERE ticker = :ticker")
    suspend fun updateStock(ticker: String , price: Double)


    @Query("SELECT SUM(totalCurrentValue) FROM stocks")
    fun getTotalCurrentValue(): Flow<Double>

    @Query("SELECT SUM(averagePrice) FROM stocks")
    fun getTotalInvested(): Flow<Double>

     @Query("SELECT COUNT(*) FROM stocks")
     fun getCount(): Flow<Int>

     @Query("SELECT * FROM stocks WHERE id = :id")
    suspend fun getStockById(id: Long): StocksEntity?

     @Query("UPDATE stocks SET quantity = :quantity , averagePrice = :price WHERE id = :id")
     suspend fun updateStockbyQuantity( id: Long , quantity : Int , price : Double)
}



