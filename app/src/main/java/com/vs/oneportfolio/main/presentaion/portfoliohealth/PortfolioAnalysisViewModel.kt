package com.vs.oneportfolio.main.presentaion.portfoliohealth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class PortfolioAnalysisViewModel(
    private val portfolioAnalyzer: PortfolioAnalyzer,
    private val inputData: GeminiData
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

    init {
        getAnalysis()
    }
    private fun getAnalysis (){
        viewModelScope.launch {
            val totalcurrentValue = inputData.getTotalporfolioValue()
            val getAssetClass = inputData.getAssetAllocation()
            val input = PortfolioInputData(
                assets = getAssetClass,
                currency = "USD",
                userContext = UserContext(),
                totalCurrentValue = totalcurrentValue
            )
            val analysis = portfolioAnalyzer.analyzePortfolio(
                portfolioData = input
            )
            Timber.i("result : $analysis")
        }
    }
    fun onAction(action: PortfolioAnalysisAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}