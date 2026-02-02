package com.vs.oneportfolio.main.presentaion.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.stocks.StockDao
import com.vs.oneportfolio.core.gemini.StockRepository
import com.vs.oneportfolio.core.socket.FinnhubTradeResponse
import com.vs.oneportfolio.core.socket.FinnhubWebSocketManager
import com.vs.oneportfolio.main.mapper.toEntity
import com.vs.oneportfolio.main.presentaion.home.HomeAction
import com.vs.oneportfolio.main.presentaion.home.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import timber.log.Timber

class HomeViewModel(

) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if(!hasLoadedInitialData) {

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000L),
            initialValue = HomeState()
        )

        fun onAction(action: HomeAction) {
            when(action) {

                else -> {}
            }
        }

}