package com.vs.oneportfolio.core.socket

import com.vs.oneportfolio.core.database.stocks.StockDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber

class EodhdWebSocketManager(
                            private val stockDao: StockDao,
                            private val scope: CoroutineScope,
                            private val json : Json
    ) {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private var isConnected = false
    private val _priceUpdates = MutableSharedFlow<String>(replay = 0)
    val priceUpdates = _priceUpdates.asSharedFlow()



    private fun connect() {
        if (webSocket != null) return // Already connecting or connected

        val request = Request.Builder().url("wss://ws.eodhistoricaldata.com/ws/us?api_token=demo").build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                // 2. ONLY NOW are we ready to send subscribe messages
                 isConnected = true
                subscribe("AAPL")
                subscribe("TSLA")
                Timber.d("Pipe is open and ready!")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                scope.launch {
                    _priceUpdates.emit(text)
                }

            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                isConnected = false
                 Timber.e("Connection Failed")
            }
        })
    }

    fun disconnect() {
        webSocket?.close(1000, "User left the screen")
        webSocket = null
        isConnected = false
    }
    fun startGlobalSync() {
        connect()
        scope.launch {
            _priceUpdates.sample(1000).collect{ jsonText->
                if (jsonText.contains("status_code") || jsonText.contains("Authorized")) {
                    Timber.d("System Message: $jsonText")
                    return@collect
                }
                val data = json.decodeFromString<TradeUpdate>(jsonText)
                Timber.Forest.i("data: $jsonText")

                stockDao.updateStock(data.s , data.p)



            }
        }
    }
    fun subscribe(symbol:String) {
        if (isConnected) {
            webSocket?.send("{\"action\":\"subscribe\",\"symbols\":\"$symbol\"}")
        }
    }
}