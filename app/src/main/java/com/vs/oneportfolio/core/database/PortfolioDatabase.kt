package com.vs.oneportfolio.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vs.oneportfolio.core.database.crypto.CryptoDao
import com.vs.oneportfolio.core.database.crypto.CryptoEntity
import com.vs.oneportfolio.core.database.crypto.history.SoldCrypto
import com.vs.oneportfolio.core.database.crypto.history.SoldCryptoDao
import com.vs.oneportfolio.core.database.fixedincome.FixedIcomeDao
import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity
import com.vs.oneportfolio.core.database.fixedincome.history.MaturedFADao
import com.vs.oneportfolio.core.database.fixedincome.history.MaturedFEntity
import com.vs.oneportfolio.core.database.metals.MetalDao
import com.vs.oneportfolio.core.database.metals.MetalEntity
import com.vs.oneportfolio.core.database.metals.history.SoldMetalDao
import com.vs.oneportfolio.core.database.metals.history.SoldMetalEntity
import com.vs.oneportfolio.core.database.realestate.RealEstateDao
import com.vs.oneportfolio.core.database.realestate.RealEstateEntity
import com.vs.oneportfolio.core.database.realestate.history.SoldEstateDao
import com.vs.oneportfolio.core.database.realestate.history.SoldEstateEntity
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.database.stocks.StocksEntity
import com.vs.oneportfolio.core.database.stocks.history.SoldStockDao
import com.vs.oneportfolio.core.database.stocks.history.SoldStockEntity

@Database(
    entities = [StocksEntity::class , CryptoEntity::class ,
                FixedIncomeEntity::class, MaturedFEntity::class , RealEstateEntity::class,
               MetalEntity::class, SoldMetalEntity::class ,
        SoldEstateEntity :: class, SoldCrypto::class , SoldStockEntity::class
               ],
    version = 11,
)
abstract class PortfolioDatabase : RoomDatabase() {
     abstract val stockDao : StockDao
     abstract val cryptoDao : CryptoDao

     abstract val fixedIcomeDao : FixedIcomeDao

     abstract  val  maturedFADao : MaturedFADao

     abstract  val realEstateDao : RealEstateDao

     abstract  val metaldao : MetalDao

     abstract val soldMetalDao : SoldMetalDao
     abstract val soldEstateDao : SoldEstateDao
     abstract val soldCryptoDao : SoldCryptoDao
     abstract val soldStockDao : SoldStockDao


}