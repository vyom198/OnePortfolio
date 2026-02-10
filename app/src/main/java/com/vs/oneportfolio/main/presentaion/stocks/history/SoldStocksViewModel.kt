package com.vs.oneportfolio.main.presentaion.stocks.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.stocks.history.SoldStockDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SoldStocksViewModel(
    private val soldStockDao: SoldStockDao
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SoldStocksState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SoldStocksState()
        )

    private fun loadData() {
        viewModelScope.launch {
            soldStockDao.getAllSoldStocks().collect { list ->
                _state.update {
                    it.copy(
                        soldstocks = list
                    )
                }

            }
        }
    }

    fun onAction(action: SoldStocksAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}
