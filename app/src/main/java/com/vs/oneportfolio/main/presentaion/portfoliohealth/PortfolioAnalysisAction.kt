package com.vs.oneportfolio.main.presentaion.portfoliohealth

sealed interface PortfolioAnalysisAction {
   data object OnClickFab : PortfolioAnalysisAction
}