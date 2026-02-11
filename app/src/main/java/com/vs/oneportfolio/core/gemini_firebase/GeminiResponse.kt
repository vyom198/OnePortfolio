package com.vs.oneportfolio.core.gemini_firebase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComprehensivePortfolioAnalysis(
    @SerialName("health_score")
    val healthScore: Int,

    @SerialName("overall_rating")
    val overallRating: String,

    @SerialName("risk_level")
    val riskLevel: String,

    @SerialName("diversification_analysis")
    val diversificationAnalysis: DiversificationAnalysis,

    @SerialName("risk_analysis")
    val riskAnalysis: RiskAnalysis,

    @SerialName("recommendations")
    val recommendations: Recommendations,

    @SerialName("performance_insights")
    val performanceInsights: PerformanceInsights,

    @SerialName("executive_summary")
    val executiveSummary: String
)

@Serializable
data class DiversificationAnalysis (
    @SerialName("diversification_score")
    val diversificationScore: Int,

    @SerialName("asset_class_breakdown")
    val assetClassBreakdown: List<AssetClassBreakdown>,

    @SerialName("sector_concentration")
    val sectorConcentration: SectorConcentration,

    @SerialName("geographic_risk")
    val geographicRisk: String
)

@Serializable
data class AssetClassBreakdown(
    @SerialName("asset_class")
    val assetClass: String,

    @SerialName("current_percentage")
    val currentPercentage: Double,

    @SerialName("recommended_percentage")
    val recommendedPercentage: Double,

    @SerialName("status")
    val status: String
)

@Serializable
data class SectorConcentration(
    @SerialName("top_sectors")
    val topSectors: List<SectorInfo>,

    @SerialName("concentration_risk")
    val concentrationRisk: String
)

@Serializable
data class SectorInfo(
    @SerialName("sector")
    val sector: String,

    @SerialName("percentage")
    val percentage: Double,

    @SerialName("risk_level")
    val riskLevel: String
)

@Serializable
data class RiskAnalysis(
    @SerialName("risk_factors")
    val riskFactors: List<RiskFactor>,

    @SerialName("volatility_assessment")
    val volatilityAssessment: String,

    @SerialName("downside_protection")
    val downsideProtection: String
)

@Serializable
data class RiskFactor(
    @SerialName("risk_type")
    val riskType: String,

    @SerialName("severity")
    val severity: String,

    @SerialName("description")
    val description: String
)

@Serializable
data class Recommendations(
    @SerialName("immediate_actions")
    val immediateActions: List<ImmediateAction>,

    @SerialName("rebalancing_suggestions")
    val rebalancingSuggestions: List<RebalancingSuggestion>
)

@Serializable
data class ImmediateAction(
    @SerialName("priority")
    val priority: String,

    @SerialName("action")
    val action: String,

    @SerialName("reason")
    val reason: String
)

@Serializable
data class RebalancingSuggestion(
    @SerialName("from_asset")
    val fromAsset: String,

    @SerialName("to_asset")
    val toAsset: String,

    @SerialName("amount_percentage")
    val amountPercentage: Double,

    @SerialName("rationale")
    val rationale: String
)

@Serializable
data class PerformanceInsights(
    @SerialName("strengths")
    val strengths: List<String>,

    @SerialName("weaknesses")
    val weaknesses: List<String>,

    @SerialName("opportunities")
    val opportunities: List<String>
)