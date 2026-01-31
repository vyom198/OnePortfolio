package com.vs.oneportfolio.app.di

import com.vs.oneportfolio.app.OnePortfolio
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module{
    single<CoroutineScope> {
        (androidApplication() as OnePortfolio).applicationScope
    }
}