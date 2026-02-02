package com.vs.oneportfolio.core.socket

import com.vs.oneportfolio.BuildConfig
import com.vs.oneportfolio.core.database.stocks.StockDao
import org.koin.dsl.module

val socketModule = module {
    single { EodhdWebSocketManager(
        get<StockDao>(), get() , get ()) }

}