package com.vs.oneportfolio.core.database.portfoliohealth

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vs.oneportfolio.core.gemini_firebase.DiversificationAnalysis
import com.vs.oneportfolio.core.gemini_firebase.PerformanceInsights
import com.vs.oneportfolio.core.gemini_firebase.Recommendations
import com.vs.oneportfolio.core.gemini_firebase.RiskAnalysis
import kotlinx.serialization.SerialName

@Entity(tableName = "portfolio_health")
@TypeConverters(PortfolioAnalysisConverters::class)
data class PortfolioHealthEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

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
    val executiveSummary: String,

    val timestamp: Long = System.currentTimeMillis()


)
