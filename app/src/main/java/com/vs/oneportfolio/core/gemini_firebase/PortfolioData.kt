package com.vs.oneportfolio.core.gemini_firebase

import com.vs.oneportfolio.core.database.crypto.CryptoDao
import com.vs.oneportfolio.core.database.fixedincome.FixedIcomeDao
import com.vs.oneportfolio.core.database.metals.MetalDao
import com.vs.oneportfolio.core.database.realestate.RealEstateDao
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.finnhubNetwork.MetalDto.GoldDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


class GeminiInputData (
    private val stockDao: StockDao,
    private val cryptoDao: CryptoDao,
    private val metalDao: MetalDao,
    private val fixedIncomeDao: FixedIcomeDao,
    private  val realEstateDao: RealEstateDao,
    private val scope : CoroutineScope
) : GeminiData{



    override suspend fun getTotalporfolioValue(): Double = withContext(Dispatchers.IO){

        val stockDeferred = async { stockDao.getTotalCurrentValueSnap() }
        val cryptoDeferred = async { cryptoDao.getTotalCurrentValueSnap() }
        val metalDeferred = async { metalDao.gettotalCurrentValueSnap() }
        val fixedIncomeDeferred = async { fixedIncomeDao.getCurrentValueSnap() }
        val realEstateDeferred = async { realEstateDao.getTotalCurrentValueSnap() }

        // All queries execute in parallel
        val stockValue = stockDeferred.await() ?: 0.0
        val cryptoValue = cryptoDeferred.await() ?: 0.0
        val metalValue = metalDeferred.await() ?: 0.0
        val fixedIncomeValue = fixedIncomeDeferred.await() ?: 0.0
        val realEstateValue = realEstateDeferred.await() ?: 0.0

        stockValue + cryptoValue + metalValue + fixedIncomeValue + realEstateValue
    }

    override suspend fun getAssetAllocation(): List<AssetClassData> = withContext(Dispatchers.IO) {
        val totalValue = getTotalporfolioValue()

        val stocksDeferred = async {
            Pair("Stocks & ETFs", stockDao.getTotalCurrentValueSnap())
        }
        val cryptoDeferred = async {
            Pair("Crypto", cryptoDao.getTotalCurrentValueSnap())
        }
        val metalsDeferred = async {
            Pair("Metals", metalDao.gettotalCurrentValueSnap())
        }
        val fixedIncomeDeferred = async {
            Pair("Fixed Income ", fixedIncomeDao.getCurrentValueSnap())
        }
        val realEstateDeferred = async {
            Pair("Real Estate", realEstateDao.getTotalCurrentValueSnap())
        }

        val (stocksAssetClass, stocksValue) = stocksDeferred.await()
        val (cryptoAssetClass, cryptoValue) = cryptoDeferred.await()
        val (metalsAssetClass, metalsValue) = metalsDeferred.await()
        val (fixedIncomeAssetClass, fixedIncomeValue) = fixedIncomeDeferred.await()
        val (realEstateAssetClass, realEstateValue) = realEstateDeferred.await()

        buildList {
            if (stocksValue > 0) {
                add(
                    AssetClassData(
                        assetClass = stocksAssetClass,
                        currentValue = stocksValue,
                        percentage = if (totalValue > 0) (stocksValue / totalValue) * 100 else 0.0,
                        holdings = stockDao.getAllStocksSnap().map { stock ->
                            HoldingData(
                                name = stock.name ?: "Unknown Stock",
                                currentValue = stock.totalCurrentValue
                            )
                        }
                    )
                )
            }

            if (cryptoValue > 0) {
                add(
                    AssetClassData(
                        assetClass = cryptoAssetClass,
                        currentValue = cryptoValue,
                        percentage = if (totalValue > 0) (cryptoValue / totalValue) * 100 else 0.0,
                        holdings = cryptoDao.getAllCryptoSnap().map { crypto ->
                            HoldingData(
                                name = crypto.name ?: "Unknown Crypto",
                                currentValue = crypto.totalCurrentValue
                            )
                        }
                    )
                )
            }

            if (metalsValue > 0) {
                add(
                    AssetClassData(
                        assetClass = metalsAssetClass,
                        currentValue = metalsValue,
                        percentage = if (totalValue > 0) (metalsValue / totalValue) * 100 else 0.0,
                        holdings = metalDao.getAllMetalsSnap().map { metal ->
                            HoldingData(
                                name = metal.label ?: "Unknown Metal",
                                currentValue = metal.currentPrice
                            )
                        }
                    )
                )
            }

            if (fixedIncomeValue > 0) {
                add(
                    AssetClassData(
                        assetClass = fixedIncomeAssetClass,
                        currentValue = fixedIncomeValue,
                        percentage = if (totalValue > 0) (fixedIncomeValue / totalValue) * 100 else 0.0,
                        holdings = fixedIncomeDao.getAllFixedIncomeSnap().map { fi ->
                            HoldingData(
                                name = fi.depositName?: "Unknown Fixed Income",
                                currentValue = fi.currentValue
                            )
                        }
                    )
                )
            }

            if (realEstateValue > 0) {
                add(
                    AssetClassData(
                        assetClass = realEstateAssetClass,
                        currentValue = realEstateValue,
                        percentage = if (totalValue > 0) (realEstateValue / totalValue) * 100 else 0.0,
                        holdings = realEstateDao.getAllRealEstatesSnap().map { re ->
                            HoldingData(
                                name = re.propertyName ?: "Unknown Real Estate",
                                currentValue = re.currentMarketValue
                            )
                        }
                    )
                )
            }
        }
    }
}

@Serializable
data class PortfolioInputData(
    @SerialName("total_current_value")
    val totalCurrentValue: Double,

    @SerialName("currency")
    val currency: String = "USD",

    @SerialName("assets")
    val assets: List<AssetClassData>,

    // Optional user context for better recommendations
    @SerialName("user_context")
    val userContext: UserContext? = null
)

@Serializable
data class AssetClassData(
    @SerialName("asset_class")
    val assetClass: String, // "Stocks", "Real Estate", "Gold", "Bonds", "Crypto", "Cash"

    @SerialName("current_value")
    val currentValue: Double,

    @SerialName("percentage")
    val percentage: Double,

    @SerialName("holdings")
    val holdings: List<HoldingData>? = null // Optional detailed breakdown
)

@Serializable
data class HoldingData(
    @SerialName("name")
    val name: String,

    @SerialName("current_value")
    val currentValue: Double,

//    @SerialName("sector")
//    val sector: String? = null, // For stocks: "Technology", "Healthcare", etc.
//
//    @SerialName("type")
//    val type: String? = null, // For real estate: "Residential", "Commercial", "REIT"
//
//    @SerialName("country")
//    val country: String? = "Global" // "USA", "India", "Global"
)

@Serializable
data class UserContext(
    @SerialName("age")
    val age: Int? = 23,

    @SerialName("risk_tolerance")
    val riskTolerance: String? = "Moderate", // "Low", "Moderate", "High"

    @SerialName("investment_goal")
    val investmentGoal: String? = "Wealth Building", // "Retirement", "Wealth Building", "Income Generation"

    @SerialName("time_horizon")
    val timeHorizon: String? = "Medium" // "Short (0-3 years)", "Medium (3-10 years)", "Long (10+ years)"
)