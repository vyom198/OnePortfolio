package com.vs.oneportfolio.main.presentaion.stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.gemini.StockRepository
import com.vs.oneportfolio.main.mapper.toEntity
import com.vs.oneportfolio.main.presentaion.model.StockUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class StockViewModel(
    private val stockRepository: StockRepository,
    private val stockDao: StockDao,

) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(StockState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
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
                                currentPrice = it.totalCurrentValue,
                                lastUpdated = it.lastUpdated
                            )
                        }
                    )
                }

            }
        }
    }


    private fun onButtonClick(){
        viewModelScope.launch {
            val userInput = state.value.text
            _state.update {
                it.copy(
                    loading = true,
                    text = ""
                )
            }
            val stockDetail = stockRepository.parseStockInput(userInput)
            if(stockDetail != null){
                stockDao.insertStock(stockDetail.toEntity())
            }
            _state.update {
                it.copy(
                    loading = false ,
                    isAdding = false
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