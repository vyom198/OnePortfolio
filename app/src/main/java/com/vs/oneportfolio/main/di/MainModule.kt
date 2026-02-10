package com.vs.oneportfolio.main.di

import com.vs.oneportfolio.core.gemini.StockRepository
import com.vs.oneportfolio.main.data.fixedAsset.notification.FANotificationService
import com.vs.oneportfolio.main.data.realestate.notification.RENotificationSerivice
import com.vs.oneportfolio.main.domain.fixedAsset.notification.FANotification
import com.vs.oneportfolio.main.domain.realestate.notification.RENotification
import com.vs.oneportfolio.main.presentaion.crypto.CryptoViewModel
import com.vs.oneportfolio.main.presentaion.crypto.history.SoldCryptoViewModel
import com.vs.oneportfolio.main.presentaion.fixedAssets.FixedAssetsViewModel
import com.vs.oneportfolio.main.presentaion.fixedAssets.history.FixedHistoryViewModel
import com.vs.oneportfolio.main.presentaion.home.HomeViewModel
import com.vs.oneportfolio.main.presentaion.metals.MetalViewModel
import com.vs.oneportfolio.main.presentaion.metals.history.SoldMetalViewModel
import com.vs.oneportfolio.main.presentaion.realestate.RealViewModel
import com.vs.oneportfolio.main.presentaion.realestate.addrealEstate.AddEstateViewModel
import com.vs.oneportfolio.main.presentaion.realestate.history.SoldEstateViewModel
import com.vs.oneportfolio.main.presentaion.stocks.StockViewModel
import com.vs.oneportfolio.main.presentaion.stocks.history.SoldStocksViewModel
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule = module {
    single<StockRepository> {
        StockRepository(get())
    }
    viewModelOf(::HomeViewModel)
    viewModelOf(::StockViewModel)
    viewModelOf(::CryptoViewModel)
    viewModelOf(::MetalViewModel)
    viewModelOf(::FixedAssetsViewModel)
    viewModelOf(::RealViewModel)
    viewModelOf(::AddEstateViewModel)
    viewModelOf(::SoldCryptoViewModel)
    viewModelOf(::FixedHistoryViewModel)
    viewModelOf(::SoldMetalViewModel)
    viewModelOf(::SoldEstateViewModel)
    viewModelOf(::SoldStocksViewModel)
    singleOf(::FANotificationService) bind FANotification::class
    singleOf(::RENotificationSerivice) bind RENotification::class


    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            isLenient = true
        }
    }
}
