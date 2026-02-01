package com.vs.oneportfolio.core.socket

import com.vs.oneportfolio.BuildConfig
import org.koin.dsl.module

val socketModule = module {
    single { FinnhubWebSocketManager(apiKey = BuildConfig.FINNHUB_API_KEY) }

}