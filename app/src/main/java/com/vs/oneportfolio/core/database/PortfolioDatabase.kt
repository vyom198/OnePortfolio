package com.vs.oneportfolio.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.database.stocks.StocksEntity

@Database(
    entities = [StocksEntity::class],
    version = 1,
)
abstract class PortfolioDatabase : RoomDatabase() {
     abstract val stockDao : StockDao
}