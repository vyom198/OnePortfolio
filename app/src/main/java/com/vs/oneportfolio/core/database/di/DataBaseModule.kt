package com.vs.oneportfolio.core.database.di

import androidx.room.Room
import com.vs.oneportfolio.core.database.PortfolioDatabase
import com.vs.oneportfolio.core.database.stocks.StockDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module{
    single<PortfolioDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            PortfolioDatabase::class.java,
            "portfolio_database"
        ).fallbackToDestructiveMigration(true).build()
    }
   single {
       get<PortfolioDatabase>().stockDao

   }



}