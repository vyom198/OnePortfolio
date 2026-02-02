package com.vs.oneportfolio.main.presentaion.stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.gemini.StockRepository
import com.vs.oneportfolio.core.socket.FinnhubTradeResponse
import com.vs.oneportfolio.core.socket.FinnhubWebSocketManager
import com.vs.oneportfolio.main.mapper.toEntity
import com.vs.oneportfolio.main.presentaion.home.HomeAction
import com.vs.oneportfolio.main.presentaion.model.StockUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import timber.log.Timber

class StockViewModel(
    private val stockRepository: StockRepository,
    private val stockDao: StockDao,
    private val socketManager: FinnhubWebSocketManager,
    private val json : Json
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(StockState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                collectCurrentPrice()
                loadStocks()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = StockState()
        )
    private fun loadStocks (){
        viewModelScope.launch {
            stockDao.getAllStocks().collect { stocks ->
                _state.update {
                    it.copy(
                        stocksList = stocks.map {
                            StockUI(
                                id = it.id,
                                ticker = it.ticker,
                                name = it.name,
                                quantity = it.quantity,
                                averagePrice = it.averagePrice,
                                currentPrice = it.currentPrice,
                                lastUpdated = it.lastUpdated
                            )
                        }
                    )
                }

            }
        }
    }
    private fun collectCurrentPrice (){
        socketManager.connect()
        viewModelScope.launch {
            stockDao.getAllStocks().collect { stocks ->
                stocks.forEach { stock ->
                    // This will run every time a new stock is added to Room
                    socketManager.subscribe(stock.ticker.uppercase())
                }
            }
        }
        viewModelScope.launch {

            socketManager.priceUpdates.collect{ jsonText->
                val data = json.decodeFromString<FinnhubTradeResponse>(jsonText)
                Timber.Forest.i("data: $jsonText")
                if(data.type == "trade"){
                    val tradeData = data.data
                    if(tradeData != null){
                        tradeData.forEach { tradeData ->
                            stockDao.updateStock(tradeData.s , tradeData.p)
                        }
                    }
                }
            }
        }
    }

    private fun onButtonClick(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true
                )
            }
            val stockDetail = stockRepository.parseStockInput(_state.value.text)
            Timber.Forest.d("stockDetail: $stockDetail")
            if(stockDetail != null){
                stockDao.insertStock(stockDetail.toEntity())
            }
            _state.update {
                it.copy(
                    loading = false
                )
            }
        }
    }
    fun onAction(action: StockAction) {
        when (action) {
             StockAction.onButtonClick -> onButtonClick()
            is StockAction.onTextChange -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            text =  action.text
                        )
                    }
                }
            }

            StockAction.onAddIconclick -> {
               _state.update {
                   it.copy(
                       isAdding = true
                   )

               }
            }

            StockAction.onDismiss ->{
                _state.update {
                    it.copy(
                        isAdding = false
                    )
                }
            }


        }
    }

}