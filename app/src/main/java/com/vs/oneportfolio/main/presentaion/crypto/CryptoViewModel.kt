package com.vs.oneportfolio.main.presentaion.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.crypto.CryptoDao
import com.vs.oneportfolio.core.database.crypto.CryptoEntity
import com.vs.oneportfolio.core.database.crypto.history.SoldCrypto
import com.vs.oneportfolio.core.database.crypto.history.SoldCryptoDao
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.database.stocks.StocksEntity
import com.vs.oneportfolio.core.finnhubNetwork.FinnHubManager
import com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos.CoinMetadata
import com.vs.oneportfolio.core.finnhubNetwork.util.Result
import com.vs.oneportfolio.main.presentaion.model.CryptoUI
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
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import timber.log.Timber
import kotlin.math.abs

class CryptoViewModel(
    private val cryptoDao: CryptoDao,
    private val finnHubManager: FinnHubManager,
    private val soldCryptoDao: SoldCryptoDao
) : ViewModel() {

    private var hasLoadedInitialData = false
    private  val evenChannel = Channel<CryptoEvent>()
    val event = evenChannel.receiveAsFlow()
    private val _state = MutableStateFlow(CryptoState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                getSearchResults()
                loadCryptos()
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

                    val result = finnHubManager.fetchCoinMetadata(query)
                    when (result) {
                        is Result.Success -> {
                            val list = listOf(result.data)
                            _state.update { it.copy(tickerList = list as List<CoinMetadata>) }
                        }
                        is Result.Error -> {
                            Timber.e("Search Error: ${result.error}")
                        }
                    }
                }
        }
    }
    private fun loadCryptos (){
        viewModelScope.launch {
           cryptoDao.getAllCrypto().collect { cryptos ->
                Timber.d("Cryptos: $cryptos")
                _state.update {
                    it.copy(
                       cryptoList = cryptos.map { it ->
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
            val oldQuantity = _state.value.currentUpdatingCrypto!!.coins
            val newQuantity = oldQuantity + quantity
            val oldInvestedamt = _state.value.currentUpdatingCrypto!!.averagePrice
            val newamt = if(quantity < 0.0 ){
                oldInvestedamt - (oldInvestedamt * abs(quantity)/oldQuantity)
            }else{
                oldInvestedamt + amt
            }
            val item = cryptoDao.getCryptoById(_state.value.currentUpdatingCrypto!!.id).copy(
                quantity = if(newQuantity<0) 0.0 else newQuantity,
                averagePrice = if(newamt < 0) 0.0 else newamt
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
                name = tickerItem.slug
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
            is CryptoAction.OnMenuClick -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            currentUpdatingCrypto = action.name,
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
                        selectedTicker = action.cryptoItem
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
            CryptoAction.onDeleteCancel -> {
                _state.update {
                    it.copy(
                        isDeleting = false,
                        currentUpdatingCrypto = null
                    )
                }
            }
            CryptoAction.onDeleteConfirm -> {
                viewModelScope.launch {
                    cryptoDao.deleteCrypto(_state.value.currentUpdatingCrypto!!.id)
                    _state.update {
                        it.copy(
                            isDeleting = false,
                            currentUpdatingCrypto = null
                        )
                    }
                }
            }
            CryptoAction.onDeleting -> {
                _state.update {
                    it.copy(
                        isDeleting = true
                    )
                }
            }
            CryptoAction.onEditShareClick -> {
                _state.update {
                    it.copy(
                        addingShare = true
                    )
                }
            }
            CryptoAction.onSoldClick -> {
                viewModelScope.launch {
                    val item = _state.value.currentUpdatingCrypto
                    if(item!=null){
                        val solItem = SoldCrypto(
                            name = item.name,
                            quantity = item.coins,
                            totalCurrentValue = item.currentPrice
                        )
                        soldCryptoDao.insertSoldCrypto(solItem)

                        cryptoDao.deleteCrypto(item.id)
                    }
                    _state.update {
                        it.copy(
                            currentUpdatingCrypto = null ,

                        )

                    }
                    evenChannel.send(CryptoEvent.onSold)

                }
            }
        }
    }

}