package com.vs.oneportfolio.main.presentaion.stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.realestate.history.SoldEstateDao
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.database.stocks.StocksEntity
import com.vs.oneportfolio.core.database.stocks.history.SoldStockDao
import com.vs.oneportfolio.core.database.stocks.history.SoldStockEntity
import com.vs.oneportfolio.core.finnhubNetwork.FinnHubManager
import com.vs.oneportfolio.core.finnhubNetwork.util.Result
import com.vs.oneportfolio.main.presentaion.model.StockUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.abs


class StockViewModel(
    private val stockDao: StockDao,
    private val finnHubManager: FinnHubManager,
    private val soldStockDao: SoldStockDao

) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(StockState())
    private val eventChannel = Channel<StockEven>()
    val events = eventChannel.receiveAsFlow()
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                getSearchResults()
                loadStocks()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = StockState()
        )

    private fun getSearchResults() {
        viewModelScope.launch {
            // Collect from the state Flow directly, not flowOf
            _state.map { it.text }
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isBlank()) return@collectLatest

                    val result = finnHubManager.getSymbols(query)
                    when (result) {
                        is Result.Success -> {
                            _state.update { it.copy(tickerList = result.data) }
                        }
                        is Result.Error -> {
                            Timber.e("Search Error: ${result.error}")
                        }
                    }
                }
        }
    }
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
                                quantity = it.quantity.toInt(),
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

   private fun  updateStock(quantity : Int , amt : Double){
       viewModelScope.launch {
           val oldQuantity = _state.value.currentUpdatingStock!!.quantity
           val oldAmt = _state.value.currentUpdatingStock!!.averagePrice
           val newQuantity = oldQuantity + quantity
           val newamt =if(quantity<0){
               oldAmt - (oldAmt * abs(quantity)/oldQuantity)
           }else{
               oldAmt + amt
           }
           stockDao.updateStockbyQuantity(
               _state.value.currentUpdatingStock!!.id,
              if(newQuantity<0) 0 else newQuantity,
               if(newamt<0) 0.0 else newamt
           )
           _state.update {
               it.copy(
                   addingShare = false,
                   currentUpdatingStock = null
               )
           }
       }
   }
    private fun onButtonClick(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true,
                    text = ""
                )
            }
            val tickerItem = _state.value.selectedTicker
            val stockItem = StocksEntity(
                ticker = tickerItem!!.symbol,
                name = tickerItem.description,
            )
            stockDao.insertStock(stockItem)

            _state.update {
                it.copy(
                    loading = false ,
                    isAdding = false,
                    selectedTicker = null,
                    tickerList = emptyList()
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

            StockAction.Clear -> {
                _state.update {
                    it.copy(
                        text = "" ,
                        selectedTicker = null,
                        tickerList = emptyList()
                    )
                }
            }
            is StockAction.onSelect -> {
                _state.update {
                    it.copy(
                        selectedTicker = action.tickerItem
                    )
                }
            }



            StockAction.onDismissUpdate -> {
                _state.update {
                    it.copy(
                        addingShare = false,
                        currentUpdatingStock = null
                    )
                }
            }
            is StockAction.onUpdateClick -> updateStock(action.quantity , action.amt)

            is StockAction.OnMenuClick -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            currentUpdatingStock = action.name,
                        )

                    }
                }
            }

            StockAction.onDelete -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isDeleting = true
                        )
                    }
                }
            }
            StockAction.onDeleteCancel -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isDeleting = false ,
                            currentUpdatingStock = null
                        )
                    }
                }
            }
            StockAction.onDeleteConfirm -> {
                viewModelScope.launch {
                    stockDao.deleteStock(
                        _state.value.currentUpdatingStock!!.id
                    )
                    _state.update {
                        it.copy(
                            isDeleting = false,
                            currentUpdatingStock = null
                        )
                    }

                }
            }
            StockAction.onEditShareClick ->{
                _state.update {
                    it.copy(
                        addingShare = true
                    )
                }
            }
            StockAction.onSoldClick -> {
                viewModelScope.launch {
                    val item = _state.value.currentUpdatingStock!!
                    val soldItem = SoldStockEntity(
                        name = item.name,
                        quantity = item.quantity.toDouble(),
                         totalCurrentValue = item.currentPrice
                    )
                    soldStockDao.insertSoldStock(soldItem)
                    stockDao.deleteStock(
                        _state.value.currentUpdatingStock!!.id
                    )
                    _state.update {
                        it.copy(
                            isDeleting = false,
                            currentUpdatingStock = null
                        )
                    }
                    eventChannel.send(StockEven.onSold)

                }
            }
        }
    }

}