package com.vs.oneportfolio.core.gemini_firebase

interface GeminiData {
    suspend fun getTotalporfolioValue() : Double
    suspend fun getAssetAllocation() : List<AssetClassData>


}