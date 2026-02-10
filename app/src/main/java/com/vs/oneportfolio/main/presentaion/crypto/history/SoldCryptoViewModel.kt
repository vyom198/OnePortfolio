package com.vs.oneportfolio.main.presentaion.crypto.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vs.oneportfolio.core.database.crypto.history.SoldCryptoDao
import com.vs.oneportfolio.core.database.metals.MetalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SoldCryptoViewModel(
    private val soldCryptoDao: SoldCryptoDao
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SoldCryptoState())
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
            initialValue = SoldCryptoState()
        )

    private fun loadData (){
        viewModelScope.launch {
            soldCryptoDao.getAllSoldCrypto().collect { soldCryptos ->
                _state.update {
                    it.copy(
                        soldCrypto =soldCryptos
                    )
                }
            }

            }
        }
    }

