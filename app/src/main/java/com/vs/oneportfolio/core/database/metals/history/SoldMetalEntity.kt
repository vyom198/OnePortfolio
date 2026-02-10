package com.vs.oneportfolio.core.database.metals.history

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "soldmetal")
data class SoldMetalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val label: String,            // e.g., "Swiss PAMP Bar" or "Wedding Ring"

    // Weight and Unit
    val weight: Double,            //"OUNCES"
    val unit: Int,             //1 "GRAMS" or 2"OUNCES"

    val karat: Int,
    val currentPrice : Double ,
)
