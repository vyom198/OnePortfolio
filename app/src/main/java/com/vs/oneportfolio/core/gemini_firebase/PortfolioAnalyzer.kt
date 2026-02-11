package com.vs.oneportfolio.core.gemini_firebase

import com.google.firebase.ai.FirebaseAI
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.type.Schema
import com.google.firebase.ai.type.generationConfig
import kotlinx.serialization.json.Json


class PortfolioAnalyzer(
    private val ai: FirebaseAI
) {

    companion object {
        val portfolioHealthSchema = Schema.obj(
            mapOf(
                "health_score" to Schema.integer(),
                "overall_rating" to Schema.string(),
                "risk_level" to Schema.string(),
                "diversification_analysis" to Schema.obj(
                    mapOf(
                        "diversification_score" to Schema.integer(),
                        "asset_class_breakdown" to Schema.array(
                            Schema.obj(mapOf(
                                "asset_class" to Schema.string(),
                                "current_percentage" to Schema.double(),
                                "recommended_percentage" to Schema.double(),
                                "status" to Schema.string()
                            ))
                        ),
                        "sector_concentration" to Schema.obj(
                            mapOf(
                                "top_sectors" to Schema.array(
                                    Schema.obj(mapOf(
                                        "sector" to Schema.string(),
                                        "percentage" to Schema.double(),
                                        "risk_level" to Schema.string()
                                    ))
                                ),
                                "concentration_risk" to Schema.string()
                            )
                        ),
                        "geographic_risk" to Schema.string()
                    )
                ),
                "risk_analysis" to Schema.obj(
                    mapOf(
                        "risk_factors" to Schema.array(
                            Schema.obj(mapOf(
                                "risk_type" to Schema.string(),
                                "severity" to Schema.string(),
                                "description" to Schema.string()
                            ))
                        ),
                        "volatility_assessment" to Schema.string(),
                        "downside_protection" to Schema.string()
                    )
                ),
                "recommendations" to Schema.obj(
                    mapOf(
                        "immediate_actions" to Schema.array(
                            Schema.obj(mapOf(
                                "priority" to Schema.string(),
                                "action" to Schema.string(),
                                "reason" to Schema.string()
                            ))
                        ),
                        "rebalancing_suggestions" to Schema.array(
                            Schema.obj(mapOf(
                                "from_asset" to Schema.string(),
                                "to_asset" to Schema.string(),
                                "amount_percentage" to Schema.integer(),
                                "rationale" to Schema.string()
                            ))
                        )
                    )
                ),
                "performance_insights" to Schema.obj(
                    mapOf(
                        "strengths" to Schema.array(Schema.string()),
                        "weaknesses" to Schema.array(Schema.string()),
                        "opportunities" to Schema.array(Schema.string())
                    )
                ),
                "executive_summary" to Schema.string()
            )
        )
    }

    private val modelWithSchema: GenerativeModel by lazy {
        ai.generativeModel(
            modelName = "gemini-2.5-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = portfolioHealthSchema
            }
        )
    }

    /**
     * Analyze portfolio using structured data
     */
    suspend fun analyzePortfolio(
        portfolioData: PortfolioInputData
    ): Result<ComprehensivePortfolioAnalysis> {
        return try {
            // Convert structured data to JSON string for the prompt
            val portfolioJson = Json.encodeToString(portfolioData)

            val prompt = buildComprehensivePrompt(portfolioData, portfolioJson)

            val response = modelWithSchema.generateContent(prompt)
            val jsonString = response.text ?: throw Exception("Empty response from AI")

            val analysis = Json.decodeFromString<ComprehensivePortfolioAnalysis>(jsonString)
            Result.success(analysis)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Build comprehensive prompt with all necessary context
     */
    private fun buildComprehensivePrompt(
        data: PortfolioInputData,
        portfolioJson: String
    ): String {
        return """
You are an expert financial advisor analyzing an investment portfolio. Provide a comprehensive, data-driven analysis.

═══════════════════════════════════════════════════════════════
PORTFOLIO SUMMARY
═══════════════════════════════════════════════════════════════

Total Portfolio Value: ${formatCurrency(data.totalCurrentValue, data.currency)}

Asset Allocation:
${buildAssetSummary(data.assets)}

${buildUserContextSection(data.userContext)}

═══════════════════════════════════════════════════════════════
DETAILED PORTFOLIO DATA (JSON)
═══════════════════════════════════════════════════════════════

$portfolioJson

═══════════════════════════════════════════════════════════════
ANALYSIS REQUIREMENTS
═══════════════════════════════════════════════════════════════

1. HEALTH SCORE (0-100):
   Calculate based on:
   - Diversification across asset classes
   - Sector concentration risks
   - Geographic diversification
   - Age-appropriate asset allocation
   - Risk management
   
   Rating Scale:
   - 80-100: Excellent (well-diversified, age-appropriate)
   - 60-79: Good (minor improvements needed)
   - 40-59: Needs Improvement (moderate risks)
   - 0-39: Poor (significant risks)

2. RISK LEVEL ASSESSMENT:
   Classify as: "Very Low", "Low", "Moderate", "High", or "Very High"
   Consider:
   - Asset class volatility
   - Concentration in single sectors/stocks
   - Geographic concentration
   - Liquidity risks

3. DIVERSIFICATION ANALYSIS:
   a) Diversification Score (0-100)
   b) Asset Class Breakdown:
      - For EACH asset class, provide:
        * Current percentage
        * Recommended percentage (based on age/risk tolerance)
        * Status: "Overweight", "Balanced", or "Underweight"
   
   c) Sector Concentration (for stocks):
      - Identify top sectors and their percentages
      - Flag if >25% in any single sector (High Risk)
      - Flag if >40% in Technology (Concentration Risk)
   
   d) Geographic Risk:
      - Assess country/region concentration
      - Recommend if >60% in single country is risky

4. RISK ANALYSIS:
   Identify specific risk factors:
   - Concentration Risk (too much in one asset/sector)
   - Market Risk (high volatility assets)
   - Liquidity Risk (illiquid assets like real estate)
   - Inflation Risk (too much cash/bonds)
   - Currency Risk (international exposure)
   
   For each risk:
   - Severity: "High", "Medium", or "Low"
   - Clear description of the specific issue

5. RECOMMENDATIONS:
   a) Immediate Actions (prioritize top 3-5):
      - Priority: "High", "Medium", "Low"
      - Specific action (e.g., "Reduce Technology sector exposure from 45% to 25%")
      - Clear reason why this is important
   
   b) Rebalancing Suggestions (be specific):
      - FROM which asset (with percentage to move)
      - TO which asset
      - Example: "Move 10% from Stocks to Bonds" with clear rationale

6. PERFORMANCE INSIGHTS (SWOT):
   - Strengths: What's working well (2-3 points)
   - Weaknesses: What needs fixing (2-3 points)
   - Opportunities: What could be added (2-3 points)

7. EXECUTIVE SUMMARY:
   Write 2-3 sentences summarizing:
   - Overall health
   - Biggest concern
   - Primary recommendation

═══════════════════════════════════════════════════════════════
GUIDELINES
═══════════════════════════════════════════════════════════════

✓ Be specific with numbers and percentages
✓ Provide actionable, concrete recommendations
✓ Consider user's age and risk tolerance if provided
✓ Flag concentration risks (>20% in single stock, >30% in single sector)
✓ Recommend age-based allocation:
  - Age 20-35: 80-90% stocks, 10-20% bonds/stable assets
  - Age 35-50: 70-80% stocks, 20-30% bonds/stable assets  
  - Age 50-65: 50-60% stocks, 40-50% bonds/stable assets
  - Age 65+: 30-40% stocks, 60-70% bonds/stable assets

✗ Avoid generic advice like "diversify more"
✗ Don't recommend specific stocks or products
✗ Don't make predictions about future returns

Provide your complete analysis in the specified JSON schema format.
        """.trimIndent()
    }

    private fun buildAssetSummary(assets: List<AssetClassData>): String {
        return assets.sortedByDescending { it.currentValue }
            .joinToString("\n") { asset ->
                val valueStr = formatCurrency(asset.currentValue, "")
                val holdingsInfo = asset.holdings?.let { holdings ->
                    "\n  Holdings: " + holdings.joinToString(", ") { h ->
                        "${h.name} ($${String.format("%,.0f", h.currentValue)})"
                    }
                } ?: ""

                "• ${asset.assetClass}: $valueStr (${asset.percentage}%)$holdingsInfo"
            }
    }

    private fun buildUserContextSection(userContext: UserContext?): String {
        if (userContext == null) return ""

        val contextParts = mutableListOf<String>()

        userContext.age?.let {
            contextParts.add("Age: $it years")
        }
        userContext.riskTolerance?.let {
            contextParts.add("Risk Tolerance: $it")
        }
        userContext.investmentGoal?.let {
            contextParts.add("Goal: $it")
        }
        userContext.timeHorizon?.let {
            contextParts.add("Time Horizon: $it")
        }

        return if (contextParts.isEmpty()) "" else {
            "\nInvestor Profile:\n" + contextParts.joinToString("\n") { "• $it" }
        }
    }

    private fun formatCurrency(value: Double, currency: String): String {
        val symbol = when (currency.uppercase()) {
            "USD" -> "$"
            "INR" -> "₹"
            "EUR" -> "€"
            "GBP" -> "£"
            else -> currency
        }
        return "$symbol${String.format("%,.2f", value)}"
    }
}

// Response data classes
