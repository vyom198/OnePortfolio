package com.vs.oneportfolio.core.gemini

import com.google.ai.client.generativeai.GenerativeModel
import com.vs.oneportfolio.core.finnhubNetwork.FinnHubManager
import kotlinx.serialization.json.Json
import timber.log.Timber

class StockRepository(
    private val model: GenerativeModel,
) {

    suspend fun parseStockInput(userInput: String): StockTransaction? {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val prompt = """
            Extract the stock transaction details from this text: "$userInput"
            Return ONLY a JSON object with these keys: 
            "name" (string, e.g. apple), 
            "quantity" (number),
            "totalBoughtPrice" (number).
        """.trimIndent()

        return try {
            val response = model.generateContent(prompt)
            val jsonText = response.text ?: return null
            Timber.d("Gemini Raw Response: $jsonText")
            json.decodeFromString<StockTransaction>(jsonText)
        } catch (e: Exception) {
            Timber.e(e, "Gemini Parsing Error")
            null
        }
    }
}