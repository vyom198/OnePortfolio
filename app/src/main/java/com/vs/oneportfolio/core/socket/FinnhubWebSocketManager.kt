package com.vs.oneportfolio.core.socket

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber

class FinnhubWebSocketManager(private val apiKey: String) {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private var isConnected = false


    private val _priceUpdates = MutableSharedFlow<String>(replay = 0)
    val priceUpdates = _priceUpdates.asSharedFlow()



    fun connect() {
        if (webSocket != null) return // Already connecting or connected

        val request = Request.Builder().url("wss://ws.finnhub.io?token=$apiKey").build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                // 2. ONLY NOW are we ready to send subscribe messages
                 isConnected = true
                Timber.d("Pipe is open and ready!")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                _priceUpdates.tryEmit(text)
                Timber.d("Incoming: $text")
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

    fun subscribe(symbol: String) {
        if (isConnected) {
            webSocket?.send("{\"type\":\"subscribe\",\"symbol\":\"$symbol\"}")
        }
    }
}