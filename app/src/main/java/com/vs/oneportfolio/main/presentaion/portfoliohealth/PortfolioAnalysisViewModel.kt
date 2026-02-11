package com.vs.oneportfolio.main.presentaion.portfoliohealth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.portfoliohealth.PortfolioHealthDao
import com.vs.oneportfolio.core.database.portfoliohealth.PortfolioHealthEntity
import com.vs.oneportfolio.core.gemini_firebase.GeminiData
import com.vs.oneportfolio.core.gemini_firebase.GeminiInputData
import com.vs.oneportfolio.core.gemini_firebase.PortfolioAnalyzer
import com.vs.oneportfolio.core.gemini_firebase.PortfolioInputData
import com.vs.oneportfolio.core.gemini_firebase.UserContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import com.vs.oneportfolio.core.finnhubNetwork.util.Result
import kotlinx.coroutines.flow.update

class PortfolioAnalysisViewModel(
    private val portfolioAnalyzer: PortfolioAnalyzer,
    private val inputData: GeminiData,
    private val portfolioHealthDao: PortfolioHealthDao
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(PortfolioAnalysisState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = PortfolioAnalysisState()
        )


    private fun getAnalysis() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            try {
                val totalCurrentValue = inputData.getTotalporfolioValue()
                val assetAllocation = inputData.getAssetAllocation()

                val input = PortfolioInputData(
                    assets = assetAllocation,
                    currency = "USD",
                    userContext = UserContext(),
                    totalCurrentValue = totalCurrentValue
                )

                Timber.d("📊 Calling Gemini with portfolio value: $totalCurrentValue")

                val result = portfolioAnalyzer.analyzePortfolio(portfolioData = input)

                // Properly handle the Result<T>
                when (result) {
                    is  Result.Success-> {
                        val analysis = result.data
                        Timber.i("✅ SUCCESS: Got analysis with health score ${result.data}")
                        _state.update {
                            it.copy(
                                loading = false,
                                error = null,
                                data = analysis
                            )
                        }
                        val analysisItem = PortfolioHealthEntity(
                            healthScore = analysis.healthScore,
                            overallRating = analysis.overallRating,
                            riskLevel = analysis.riskLevel,
                            diversificationAnalysis = analysis.diversificationAnalysis,
                            riskAnalysis = analysis.riskAnalysis,
                            recommendations = analysis.recommendations,
                            performanceInsights = analysis.performanceInsights,
                            executiveSummary = analysis.executiveSummary
                        )
                        portfolioHealthDao.insertPortfolioAnalysis(analysisItem)

                    }

                    is Result.Error -> {
                        val exception = result.error
                        Timber.e("❌ ERROR: $exception")
                        _state.update {
                            it.copy(
                                loading = false,
                                error = exception.toString(),
                                data = null
                            )
                        }
                    }
                }

            } catch (e: Exception) {
                Timber.e(e, "🔥 Unexpected error in getAnalysis")

            }
        }
    }
    fun onAction(action: PortfolioAnalysisAction) {
        when (action) {
            PortfolioAnalysisAction.OnClickFab -> getAnalysis()
        }
    }

}