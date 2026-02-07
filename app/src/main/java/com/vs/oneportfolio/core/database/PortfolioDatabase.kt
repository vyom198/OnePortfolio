package com.vs.oneportfolio.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vs.oneportfolio.core.database.crypto.CryptoDao
import com.vs.oneportfolio.core.database.crypto.CryptoEntity
import com.vs.oneportfolio.core.database.fixedincome.FixedIcomeDao
import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity
import com.vs.oneportfolio.core.database.fixedincome.history.MaturedFADao
import com.vs.oneportfolio.core.database.fixedincome.history.MaturedFEntity
import com.vs.oneportfolio.core.database.realestate.RealEstateDao
import com.vs.oneportfolio.core.database.realestate.RealEstateEntity
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.database.stocks.StocksEntity

@Database(
    entities = [StocksEntity::class , CryptoEntity::class ,
        FixedIncomeEntity::class, MaturedFEntity::class , RealEstateEntity::class],
    version = 6,
)
abstract class PortfolioDatabase : RoomDatabase() {
     abstract val stockDao : StockDao
     abstract val cryptoDao : CryptoDao

     abstract val fixedIcomeDao : FixedIcomeDao

     abstract  val  maturedFADao : MaturedFADao

     abstract  val realEstateDao : RealEstateDao

}