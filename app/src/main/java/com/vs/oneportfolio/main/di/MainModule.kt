package com.vs.oneportfolio.main.di

import com.vs.oneportfolio.core.gemini.StockRepository
import com.vs.oneportfolio.main.presentaion.HomeViewModel
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    single<StockRepository> {
        StockRepository(get())
    }
    viewModelOf(::HomeViewModel)
    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            isLenient = true
        }
    }
}
