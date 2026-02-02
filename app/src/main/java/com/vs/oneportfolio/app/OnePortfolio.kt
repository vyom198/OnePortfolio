package com.vs.oneportfolio.app

import android.app.Application
import com.vs.oneportfolio.BuildConfig
import com.vs.oneportfolio.app.di.appModule
import com.vs.oneportfolio.core.database.di.databaseModule
import com.vs.oneportfolio.core.gemini.geminiModule
import com.vs.oneportfolio.core.socket.socketModule
import com.vs.oneportfolio.main.di.mainModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class OnePortfolio : Application() {
    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
   //private val socketManager :  EodhdWebSocketManager by inject()
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
                socketModule

            )
        }
     //   socketManager.startGlobalSync()


    }
}