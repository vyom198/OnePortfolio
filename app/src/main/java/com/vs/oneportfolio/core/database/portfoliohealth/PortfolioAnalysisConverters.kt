package com.vs.oneportfolio.core.database.portfoliohealth

import androidx.room.TypeConverter
import com.vs.oneportfolio.core.gemini_firebase.DiversificationAnalysis
import com.vs.oneportfolio.core.gemini_firebase.PerformanceInsights
import com.vs.oneportfolio.core.gemini_firebase.Recommendations
import com.vs.oneportfolio.core.gemini_firebase.RiskAnalysis
import kotlinx.serialization.json.Json

class PortfolioAnalysisConverters {
    
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    // DiversificationAnalysis Converter
    @TypeConverter
    fun fromDiversificationAnalysis(value: DiversificationAnalysis): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toDiversificationAnalysis(value: String): DiversificationAnalysis {
        return json.decodeFromString(value)
    }

    // RiskAnalysis Converter
    @TypeConverter
    fun fromRiskAnalysis(value: RiskAnalysis): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toRiskAnalysis(value: String): RiskAnalysis {
        return json.decodeFromString(value)
    }

    // Recommendations Converter
    @TypeConverter
    fun fromRecommendations(value: Recommendations): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toRecommendations(value: String): Recommendations {
        return json.decodeFromString(value)
    }

    // PerformanceInsights Converter
    @TypeConverter
    fun fromPerformanceInsights(value: PerformanceInsights): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toPerformanceInsights(value: String): PerformanceInsights {
        return json.decodeFromString(value)
    }

    // List<String> Converter (for strengths, weaknesses, opportunities)
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return json.decodeFromString(value)
    }
}