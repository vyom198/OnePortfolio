package com.vs.oneportfolio.core.finnhubNetwork.di

import com.vs.oneportfolio.core.finnhubNetwork.FinnHubManager
import com.vs.oneportfolio.core.finnhubNetwork.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val networkModule = module{
    single<HttpClientEngine> {
        OkHttp.create()
    }

    // Provide the HttpClient as a singleton
    single<HttpClient> {
        createHttpClient(get())
    }
     single<FinnHubManager>{
         FinnHubManager(
             get()
         )
     }
}