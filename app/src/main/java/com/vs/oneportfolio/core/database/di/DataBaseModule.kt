package com.vs.oneportfolio.core.database.di

import androidx.room.Room
import com.vs.oneportfolio.core.AlarmManager.AlarmScheduler
import com.vs.oneportfolio.core.AlarmManager.PortfolioAlarmScheduler
import com.vs.oneportfolio.core.database.PortfolioDatabase
import com.vs.oneportfolio.core.database.stocks.StockDao
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

val databaseModule = module {
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
    single {
        get<PortfolioDatabase>().cryptoDao

    }

    single {
        get<PortfolioDatabase>().fixedIcomeDao

    }
    single {
        get<PortfolioDatabase>().maturedFADao

    }

    single {
        get<PortfolioDatabase>().realEstateDao

    }


    val alarmModule = module {

        singleOf(::PortfolioAlarmScheduler) bind AlarmScheduler::class


    }
}