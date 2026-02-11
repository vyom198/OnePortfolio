package com.vs.oneportfolio.main.presentaion.portfoliohealth

import com.vs.oneportfolio.core.gemini_firebase.ComprehensivePortfolioAnalysis

data class PortfolioAnalysisState(
   val loading: Boolean = false,
    val error : String? = null,
    val data : ComprehensivePortfolioAnalysis? = null
)