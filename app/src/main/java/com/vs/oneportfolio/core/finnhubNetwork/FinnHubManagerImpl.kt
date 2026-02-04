package com.vs.oneportfolio.core.finnhubNetwork

import com.vs.oneportfolio.BuildConfig
import com.vs.oneportfolio.core.finnhubNetwork.util.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import com.vs.oneportfolio.core.finnhubNetwork.util.Result
class FinnHubManager(
    private val httpClient: HttpClient
) {
    private val API_TOKEN = BuildConfig.FINNHUB_API_KEY
    suspend fun getQuotePrice(ticker: String): Result<Double, NetworkError> {
        val response = try {
            httpClient.get(
                urlString = "https://finnhub.io/api/v1/quote"
            ) {
                parameter("symbol", ticker)
                parameter("token", API_TOKEN)
            }
        } catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when(response.status.value) {
            in 200..299 -> {
                val response = response.body<FinnHubQuoteDto>()
                Result.Success(response.c)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun getSymbols(query: String): Result<List<StockTicker>, NetworkError> {
        val response = try {
            httpClient.get("https://finnhub.io/api/v1/search") {
                parameter("q", query)
                parameter("token", API_TOKEN)
            }
        } catch (e: Exception) {
            val error = when(e) {
                is UnresolvedAddressException -> NetworkError.NO_INTERNET
                is SerializationException -> NetworkError.SERIALIZATION
                else -> NetworkError.UNKNOWN
            }
            return Result.Error(error)
        }

        return when (response.status.value) {
            in 200..299 -> {
                // IMPORTANT: Parse as the Wrapper class, then return the .result list
                val searchResponse = response.body<FinnHubSearchResponse>()
                Result.Success(searchResponse.result)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

}