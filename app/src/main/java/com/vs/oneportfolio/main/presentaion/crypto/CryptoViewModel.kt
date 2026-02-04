package com.vs.oneportfolio.main.presentaion.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.crypto.CryptoDao
import com.vs.oneportfolio.core.database.crypto.CryptoEntity
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.database.stocks.StocksEntity
import com.vs.oneportfolio.core.finnhubNetwork.FinnHubManager
import com.vs.oneportfolio.core.finnhubNetwork.util.Result
import com.vs.oneportfolio.main.presentaion.model.CryptoUI
import com.vs.oneportfolio.main.presentaion.model.StockUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class CryptoViewModel(
    private val cryptoDao: CryptoDao,
    private val finnHubManager: FinnHubManager
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CryptoState())
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
            started = SharingStarted.Companion.WhileSubscribed(5_000L),
            initialValue = CryptoState()
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
                        is com.vs.oneportfolio.core.finnhubNetwork.util.Result.Success -> {
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
           cryptoDao.getAllCrypto().collect { stocks ->
                _state.update {
                    it.copy(
                       cryptoList = stocks.map {
                            CryptoUI(
                                id = it.id,
                                ticker = it.ticker,
                                name = it.name,
                                coins = it.quantity,
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

    private fun  updateStock(quantity : Double , amt : Double){
        viewModelScope.launch {
            val newQuantity = _state.value.currentUpdatingCrypto!!.coins + quantity
            val newamt = _state.value.currentUpdatingCrypto!!.averagePrice + amt
            val item = cryptoDao.getCryptoById(_state.value.currentUpdatingCrypto!!.id).copy(
                quantity = if(newQuantity<0) 0.0 else newQuantity,
                averagePrice = if(newamt<0) 0.0 else newamt
            )
            cryptoDao.insertCrypto(item)
            _state.update {
                it.copy(
                    addingShare = false,
                     currentUpdatingCrypto = null
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
            val cryptoItem = CryptoEntity(
                ticker = tickerItem!!.symbol,
                name = tickerItem.description,
            )
            cryptoDao.insertCrypto(cryptoItem)

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
    fun onAction(action: CryptoAction) {
        when (action) {
            is CryptoAction.AddShare -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            currentUpdatingCrypto = action.name,
                            addingShare = true
                        )

                    }
                }
            }
            CryptoAction.Clear -> {
                _state.update {
                    it.copy(
                        text = "" ,
                        selectedTicker = null,
                        tickerList = emptyList()
                    )
                }
            }
            CryptoAction.onAddIconclick ->{
                _state.update {
                    it.copy(
                        isAdding = true
                    )

                }
            }
            CryptoAction.onButtonClick -> onButtonClick()
            CryptoAction.onDismiss ->{
                _state.update {
                    it.copy(
                        isAdding = false
                    )
                }

            }
            CryptoAction.onDismissUpdate -> {
                _state.update {
                    it.copy(
                        addingShare = false
                    )
                }
            }
            is CryptoAction.onSelect -> {
                _state.update {
                    it.copy(
                        selectedTicker = action.tickerItem
                    )
                }
            }
            is CryptoAction.onTextChange -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            text = action.text
                        )
                    }
                }
            }
            is CryptoAction.onUpdateClick -> updateStock(action.quantity , action.amt)
        }
    }

}