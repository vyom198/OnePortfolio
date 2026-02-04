package com.vs.oneportfolio.app

import android.app.Application
import com.vs.oneportfolio.BuildConfig
import com.vs.oneportfolio.app.di.appModule
import com.vs.oneportfolio.core.database.di.databaseModule
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.finnhubNetwork.FinnHubManager
import com.vs.oneportfolio.core.finnhubNetwork.di.networkModule
import com.vs.oneportfolio.core.finnhubNetwork.util.Result
import com.vs.oneportfolio.core.gemini.geminiModule
import com.vs.oneportfolio.core.socket.socketModule
import com.vs.oneportfolio.main.di.mainModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class OnePortfolio : Application() {
    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
   //private val socketManager :  EodhdWebSocketManager by inject()
    private val networkManager : FinnHubManager by inject()
    private val stockDao : StockDao by inject()
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@OnePortfolio)
            modules(
                appModule,
                mainModule,
                databaseModule,
                geminiModule,
                socketModule ,
                networkModule

            )
        }
     //   socketManager.startGlobalSync()

        startPriceSync()

    }

    private fun startPriceSync() {
        applicationScope.launch {
            // 1. A flow that emits every 3 minutes
            val timerFlow = flow {
                while (true) {
                    emit(Unit)
                    delay(3 * 60 * 1000) // 3 minutes
                }
            }

            // 2. A flow of tickers from the DB (emits whenever DB changes)
            val tickerFlow = stockDao.getAllStocks().distinctUntilChanged()

            // 3. Combine them: Update prices if timer ticks OR tickers change
            combine(timerFlow, tickerFlow) { _, stocks ->
                stocks
            }.collect { stocks ->
                stocks.forEach { stock ->
                    val result = networkManager.getQuotePrice(stock.ticker)
                    if (result is Result.Success) {
                        stockDao.updateStock(stock.ticker, result.data)
                    }
                }
            }
        }
    }
}