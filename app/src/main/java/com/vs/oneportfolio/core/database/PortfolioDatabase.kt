package com.vs.oneportfolio.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vs.oneportfolio.core.database.crypto.CryptoDao
import com.vs.oneportfolio.core.database.crypto.CryptoEntity
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.database.stocks.StocksEntity

@Database(
    entities = [StocksEntity::class , CryptoEntity::class],
    version = 2,
)
abstract class PortfolioDatabase : RoomDatabase() {
     abstract val stockDao : StockDao
     abstract val cryptoDao : CryptoDao
}