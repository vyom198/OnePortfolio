package com.vs.oneportfolio.main.presentaion.portfoliohealth.history

import com.vs.oneportfolio.core.database.portfoliohealth.PortfolioHealthEntity

data class PortfolioHealthHistoryState(
   val data : List<PortfolioHealthEntity> = emptyList()
)