package com.vs.oneportfolio.main.presentaion.metals.model

data class WeightResult(val weightInOz: Double, val unitId: Int)
fun convertToOz(input: String, unitName: String): WeightResult {
    val numericValue = input.toDoubleOrNull() ?: 0.0

    // Define our conversion logic and IDs
    return when (unitName.lowercase()) {
        "g" -> WeightResult(numericValue * 0.035274, 1)
        "kg" -> WeightResult(numericValue * 35.274, 2)
        "oz" -> WeightResult(numericValue * 1.0, 3)
        else -> WeightResult(numericValue * 0.035274, 1) // Default to grams
    }
}
fun convertFromOz(weightInOz: Double, unitId: Int): Double {
    return when (unitId) {
        1 -> weightInOz / 0.035274  // Oz to Grams
        2 -> weightInOz / 35.274    // Oz to Kilograms
        3 -> weightInOz             // Already in Ounces
        else -> weightInOz / 0.035274 // Default fallback to Grams
    }
}
object WeightUnitMapper {
    private val idToString = mapOf(1 to "g", 2 to "kg", 3 to "oz")
    private val stringToId = idToString.entries.associate { it.value to it.key }

    // Convert ID -> String (e.g., 2 -> "kg")
    fun getString(id: Int): String = idToString[id] ?: "g"

    // Convert String -> ID (e.g., "kg" -> 2)
    fun getId(unit: String): Int = stringToId[unit.lowercase()] ?: 1
}
val list = listOf("g", "kg","oz")