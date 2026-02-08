package com.vs.oneportfolio.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.vs.oneportfolio.BuildConfig
import com.vs.oneportfolio.app.di.appModule
import com.vs.oneportfolio.core.database.crypto.CryptoDao
import com.vs.oneportfolio.core.database.di.databaseModule
import com.vs.oneportfolio.core.database.fixedincome.FixedIcomeDao
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.finnhubNetwork.FinnHubManager
import com.vs.oneportfolio.core.finnhubNetwork.di.networkModule
import com.vs.oneportfolio.core.finnhubNetwork.util.Result
import com.vs.oneportfolio.core.gemini.geminiModule
import com.vs.oneportfolio.core.socket.socketModule
import com.vs.oneportfolio.main.data.fixedAsset.notification.FANotificationService
import com.vs.oneportfolio.main.data.realestate.notification.RENotificationSerivice
import com.vs.oneportfolio.main.di.mainModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class OnePortfolio : Application() {
    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
   //private val socketManager :  EodhdWebSocketManager by inject()
    private val networkManager : FinnHubManager by inject()

    private val stockDao : StockDao by inject()
    private val cryptoDao : CryptoDao by inject()
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
        startCrptoPriceSync()
        createNotificationChannel()
        createNotificationChannelForRE()
    }

    private fun createNotificationChannelForRE() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                RENotificationSerivice.RE_CHANNEL_ID,
                RENotificationSerivice.RE_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used for Alerting about real estates"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                FANotificationService.FA_CHANNEL_ID,
                FANotificationService.FA_CHAANEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used for Alerting about Fixed Assets"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
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

    private fun startCrptoPriceSync(){
        applicationScope.launch {
            val timerFlow = flow {
                while (true) {
                    emit(Unit)
                    delay(1* 60 * 1000)
                }
            }
            val cryptoFlow = cryptoDao.getAllCrypto().distinctUntilChanged()
            combine(timerFlow, cryptoFlow) { _, cryptos ->
                cryptos
            }.collect { cryptos ->
                cryptos.forEach { crypto ->
                    val result = networkManager.fetchCoinQuote(crypto.name)
                    Timber.d("result: $result")
                    if (result is Result.Success) {
                        cryptoDao.updateStockbyId(crypto.id, result.data?.price?:0.0 , result.data?.lastUpdated?:""  )
                    }
                }
            }
        }
    }
}