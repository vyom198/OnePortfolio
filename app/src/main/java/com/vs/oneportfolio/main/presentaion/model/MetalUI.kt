package com.vs.oneportfolio.main.presentaion.model

import kotlin.math.abs

data class MetalUI(
    val id: Long = 0,
    val label: String,            // e.g., "Swiss PAMP Bar" or "Wedding Ring"

    // Weight and Unit
    val weight: Double,            //"OUNCES"
    val unit: Int,             //1 "GRAMS" or 2"OUNCES"

    // Purity (Essential for valuation)
    val karat: Int, // 24 (Pure), 22, 18, 14, etc.
    val currentPrice : Double ,
    // Financial Tracking
    val purchasePrice: Double,    // Total amount paid
    val purchaseDate: Long,       // Timestamp for "15 feb" formatting
    // Diversification/Metadata
    val storageLocation: String?  // "Bank Vault", "Home Safe"
){
    val gainOrLoss
        get() = currentPrice - purchasePrice

    val isPositive
        get() = gainOrLoss >= 0

    val abs
        get() = abs(gainOrLoss)
}
