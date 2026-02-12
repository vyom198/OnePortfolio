package com.vs.oneportfolio.app.di

import com.vs.oneportfolio.app.OnePortfolio
import com.vs.oneportfolio.core.workManager.RescheduleWorker
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import kotlin.coroutines.EmptyCoroutineContext.get

val appModule = module{
    single<CoroutineScope> {
        (androidApplication() as OnePortfolio).applicationScope
    }

     worker { RescheduleWorker(get(), get(), get(), get(), get()) }
}