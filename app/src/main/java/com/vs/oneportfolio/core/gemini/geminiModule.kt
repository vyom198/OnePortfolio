package com.vs.oneportfolio.core.gemini

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.vs.oneportfolio.BuildConfig
import org.koin.dsl.module

val geminiModule = module {
    single {
        GenerativeModel(
            modelName = "gemini-2.5-flash-lite",
            apiKey = BuildConfig.GEMINI_API_KEY ,
            generationConfig = generationConfig {
                responseMimeType = "application/json"
            }
        )
    }
}