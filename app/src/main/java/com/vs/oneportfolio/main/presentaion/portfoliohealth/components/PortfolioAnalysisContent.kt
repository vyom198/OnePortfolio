package com.vs.oneportfolio.main.presentaion.portfoliohealth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.core.gemini_firebase.ComprehensivePortfolioAnalysis
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.cards.AssetAllocationCard
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.cards.CriticalIssuesCard
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.cards.ExecutiveSummaryCard
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.cards.HealthScoreCard
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.cards.QuickActionsCard
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.cards.RebalancingCard
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.cards.RiskFactorsCard
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.cards.SectorConcentrationCard
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.cards.StrengthsWeaknessesCard

@Composable
fun PortfolioAnalysisContent(
    analysis: ComprehensivePortfolioAnalysis,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. HEALTH SCORE HEADER
        item {
            HealthScoreCard(analysis)
        }
        
        // 2. CRITICAL ISSUES (Top 3)
        item {
            CriticalIssuesCard(analysis)
        }
        
        // 3. QUICK ACTIONS (Top 3)
        item {
            QuickActionsCard(analysis.recommendations.immediateActions)
        }
        
        // 4. ASSET ALLOCATION COMPARISON
        item {
            AssetAllocationCard(analysis.diversificationAnalysis.assetClassBreakdown)
        }
        
        // 5. REBALANCING SUGGESTIONS
        if (analysis.recommendations.rebalancingSuggestions.isNotEmpty()) {
            item {
                RebalancingCard(analysis.recommendations.rebalancingSuggestions)
            }
        }
        
        // 6. RISK FACTORS
        item {
            RiskFactorsCard(analysis.riskAnalysis.riskFactors)
        }
        
        // 7. SECTOR CONCENTRATION
        item {
            SectorConcentrationCard(analysis.diversificationAnalysis.sectorConcentration)
        }
        
        // 8. STRENGTHS & WEAKNESSES
        item {
            StrengthsWeaknessesCard(analysis.performanceInsights)
        }
        
        // 9. EXECUTIVE SUMMARY
        item {
            ExecutiveSummaryCard(analysis.executiveSummary)
        }
    }
}